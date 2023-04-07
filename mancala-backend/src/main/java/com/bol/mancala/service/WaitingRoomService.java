package com.bol.mancala.service;

import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomState;
import com.bol.mancala.util.LocalDateTimeUtil;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

/**
 * Waiting Room service
 */
@Validated
@Service
@AllArgsConstructor
public class WaitingRoomService {

    private static final String ERROR_WAITING_ROOM_NOT_FOUND = "Waiting room was not found by uuid=%s";
    private static final String ERROR_PLAYER_IS_ALREADY_IN_WAITING_ROOM = "Player with uuid=%s is already in a waiting room";
    private static final String ERROR_PLAYER_NOT_FOUND = "Player was not found by uuid=%s";

    private final PlayerService playerService;

    //TODO: get stats
    /** Waiting Rooms */
    private final Map<UUID, WaitingRoom> uuidToWaitingRoomOpenedMap = new ConcurrentHashMap<>();

    /** Creates Waiting Room with the specified player and returns it */
    public WaitingRoom createFor(@NotNull UUID playerUuid) {
        validatePlayerBy(playerUuid);

        WaitingRoom waitingRoom = WaitingRoom.builder().waitingPlayerUuid(playerUuid).build();
        uuidToWaitingRoomOpenedMap.put(waitingRoom.getUuid(), waitingRoom);

        return waitingRoom;
    }

    /** Joins the earliest waiting room or creates a new one for the specified player and returns it */
    public WaitingRoom joinOrCreateFor(@NotNull UUID playerUuid) {
        validatePlayerExistsBy(playerUuid);

        WaitingRoom waitingRoomExistingWithPlayer = getExistingWaitingRoomWithPlayer(playerUuid);
        if (waitingRoomExistingWithPlayer != null) {
            return waitingRoomExistingWithPlayer;
        }

        Optional<WaitingRoom> waitingRoomExistingOp = uuidToWaitingRoomOpenedMap.values()
                .stream()
                .filter(waitingRoom -> WaitingRoomState.OPENED.equals(waitingRoom.getState()))
                .min(Comparator.comparing(WaitingRoom::getCreatedDateTime));

        return waitingRoomExistingOp.map(waitingRoom -> join(waitingRoom, playerUuid))
                .orElseGet(() -> createFor(playerUuid));
    }

    /** Returns {@code true} if Waiting Room with the specified uuid exists; otherwise - {@code false} */
    public boolean existsBy(@NotNull UUID uuid) {
        return uuidToWaitingRoomOpenedMap.containsKey(uuid);
    }

    /** Returns Waiting Room by its uuid */
    public WaitingRoom getBy(@NotNull UUID uuid) {
        WaitingRoom waitingRoom = uuidToWaitingRoomOpenedMap.get(uuid);
        if (waitingRoom == null) {
            throw new IllegalArgumentException(format(ERROR_WAITING_ROOM_NOT_FOUND, uuid));
        }
        return waitingRoom;
    }

    /** Changes Waiting Room state by its uuid */
    public void changeStateBy(@NotNull UUID uuid, @NotNull WaitingRoomState state, @NotNull UUID joinedPlayerUuid) {
        //TODO: refactor this method and add validateStateForWaitingRoom()
        // as long as for 'game has started' state joinedPlayerUuid should exist in waitingRoom
        validatePlayerExistsBy(joinedPlayerUuid);

        WaitingRoom waitingRoom = uuidToWaitingRoomOpenedMap.get(uuid);

        WaitingRoom waitingRoomClosed = waitingRoom.toBuilder()
                .state(state)
                .joinedPlayerUuid(joinedPlayerUuid)
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToWaitingRoomOpenedMap.replace(waitingRoomClosed.getUuid(), waitingRoomClosed);
    }

    /** Changes Waiting Room state by its uuid without the joined player */
    public void changeStateBy(@NotNull UUID uuid, @NotNull WaitingRoomState state) {
        validateWaitingRoomExistsBy(uuid);
        validateStateWithoutJoinedPlayer(state);

        changeStateBy(uuid, state, null);
    }

    /** Returns all Waiting Rooms */
    public Set<WaitingRoom> getAll() {
        return new HashSet<>(uuidToWaitingRoomOpenedMap.values());
    }

    private static void validateStateWithoutJoinedPlayer(@NotNull WaitingRoomState state) {
        if (WaitingRoomState.GAME_SESSION_STARTED == state) {
            throw new IllegalArgumentException("joinedPlayerUuid is required");
        }
    }

    private WaitingRoom join(@NotNull WaitingRoom waitingRoom, @NotNull UUID playerUuid) {
        WaitingRoom waitingRoomJoined = waitingRoom.toBuilder()
                .joinedPlayerUuid(playerUuid)
                .state(WaitingRoomState.WAITING_FOR_GAME_SESSION)
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToWaitingRoomOpenedMap.replace(waitingRoom.getUuid(), waitingRoomJoined);

        return waitingRoomJoined;
    }

    private WaitingRoom getExistingWaitingRoomWithPlayer(@NotNull UUID playerUuid) {
        return uuidToWaitingRoomOpenedMap.values().stream()
                .filter(waitingRoom -> playerUuid.equals(waitingRoom.getWaitingPlayerUuid()))
                .filter(waitingRoom -> WaitingRoomState.OPENED.equals(waitingRoom.getState()))
                .findFirst()
                .orElse(null);
    }

    private void validateWaitingRoomExistsBy(@NotNull UUID waitingRoomUuid) {
        if (!uuidToWaitingRoomOpenedMap.containsKey(waitingRoomUuid)) {
            throw new IllegalArgumentException(format(ERROR_WAITING_ROOM_NOT_FOUND, waitingRoomUuid));
        }
    }

    private void validatePlayerExistsBy(@NotNull UUID playerUuid) {
        if (playerUuid != null && !playerService.existBy(playerUuid)) {
            throw new IllegalArgumentException(format(ERROR_PLAYER_NOT_FOUND, playerUuid));
        }
    }

    private void validatePlayerBy(@NotNull UUID playerUuid) {
        validatePlayerExistsBy(playerUuid);
        validatePlayerIsNotInAnyOpenedWaitingRoom(playerUuid);
    }

    private void validatePlayerIsNotInAnyOpenedWaitingRoom(@NotNull UUID playerUuid) {
        boolean playerIsAlreadyInWaitingRoom = uuidToWaitingRoomOpenedMap.values().stream()
                .anyMatch(waitingRoom -> waitingRoom.getWaitingPlayerUuid().equals(playerUuid)
                        && WaitingRoomState.OPENED.equals(waitingRoom.getState()));
        if (playerIsAlreadyInWaitingRoom) {
            throw new IllegalArgumentException(format(ERROR_PLAYER_IS_ALREADY_IN_WAITING_ROOM, playerUuid));
        }
    }

}

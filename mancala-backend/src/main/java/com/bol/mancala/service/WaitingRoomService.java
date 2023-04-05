package com.bol.mancala.service;

import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomState;
import com.bol.mancala.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
@Service
@AllArgsConstructor
public class WaitingRoomService {

    private static final String ERROR_WAITING_ROOM_NOT_FOUND = "Waiting room was not found by uuid=%s";
    private static final String ERROR_PLAYER_IS_ALREADY_IN_WAITING_ROOM = "Player with uuid=%s is already in a waiting room";
    private static final String ERROR_PLAYER_NOT_FOUND = "Player was not found by uuid=%s";

    private final PlayerService playerService;

    /** Opened Waiting Rooms */
    private final Map<UUID, WaitingRoom> uuidToWaitingRoomOpenedMap = new ConcurrentHashMap<>();

    //TODO: get stats from waiting room history
    /** Closed Waiting Rooms - history */
    private final Map<UUID, WaitingRoom> uuidToWaitingRoomClosedMap = new ConcurrentHashMap<>();

    /** Creates Waiting Room with the specified player and returns it */
    public WaitingRoom create(UUID playerUuid) {
        validatePlayerBy(playerUuid);

        WaitingRoom waitingRoom = WaitingRoom.builder().waitingPlayerUuid(playerUuid).build();
        uuidToWaitingRoomOpenedMap.put(waitingRoom.getUuid(), waitingRoom);

        return waitingRoom;
    }

    /** Joins the earliest waiting room or creates a new one for the specified player and returns it */
    public WaitingRoom joinOrCreate(UUID playerUuid) {
        validatePlayerExistsBy(playerUuid);

        WaitingRoom waitingRoomExistingWithPlayer = getExistingWaitingRoomWithPlayer(playerUuid);
        if (waitingRoomExistingWithPlayer != null) {
            return waitingRoomExistingWithPlayer;
        }

        Optional<WaitingRoom> waitingRoomExistingOp = uuidToWaitingRoomOpenedMap.values()
                .stream()
                .filter(waitingRoom -> WaitingRoomState.OPENED.equals(waitingRoom.getState()))
                .min(Comparator.comparing(WaitingRoom::getCreatedDateTime));

        return waitingRoomExistingOp.map(waitingRoom -> join(playerUuid, waitingRoom))
                .orElseGet(() -> create(playerUuid));
    }

    /** Returns {@code true} if Waiting Room with the specified uuid exists; otherwise - {@code false} */
    public boolean existsBy(UUID uuid) {
        return uuidToWaitingRoomOpenedMap.containsKey(uuid);
    }

    /** Returns Waiting Room by its uuid */
    public WaitingRoom getBy(UUID uuid) {
        return Optional.ofNullable(uuidToWaitingRoomOpenedMap.get(uuid)).orElseThrow(
                () -> new IllegalArgumentException(format(ERROR_WAITING_ROOM_NOT_FOUND, uuid)));
    }

    /** Closes Waiting Room by its uuid */
    public void close(UUID uuid, WaitingRoomState state, UUID joinedPlayerUuid) {
        //TODO: refactor this method and add validateStateForWaitingRoom()
        // as long as for 'game has started' state joinedPlayerUuid should exist in waitingRoom
        validatePlayerExistsBy(joinedPlayerUuid);

        WaitingRoom waitingRoom = uuidToWaitingRoomOpenedMap.remove(uuid);

        WaitingRoom waitingRoomClosed = waitingRoom.toBuilder()
                .state(state)
                .joinedPlayerUuid(joinedPlayerUuid)
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToWaitingRoomClosedMap.put(waitingRoomClosed.getUuid(), waitingRoomClosed);
    }

    /** Closes Waiting Room by its uuid without the joined player */
    public void close(UUID uuid, WaitingRoomState state) {
        validateWaitingRoomExistsBy(uuid);
        validateStateWithoutJoinedPlayer(state);

        close(uuid, state, null);
    }

    /** Returns all Waiting Rooms */
    public Set<WaitingRoom> getAll() {
        HashSet<WaitingRoom> waitingRooms = new HashSet<>(uuidToWaitingRoomOpenedMap.values());
        waitingRooms.addAll(uuidToWaitingRoomClosedMap.values());
        return waitingRooms;
    }

    private static void validateStateWithoutJoinedPlayer(WaitingRoomState state) {
        if (WaitingRoomState.GAME_SESSION_STARTED == state) {
            throw new IllegalArgumentException("joinedPlayerUuid is required");
        }
    }

    private WaitingRoom join(UUID playerUuid, WaitingRoom waitingRoom) {
        WaitingRoom waitingRoomJoined = waitingRoom.toBuilder()
                .joinedPlayerUuid(playerUuid)
                .state(WaitingRoomState.WAITING_FOR_GAME_SESSION)
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToWaitingRoomOpenedMap.put(waitingRoomJoined.getUuid(), waitingRoomJoined);

        return waitingRoomJoined;
    }

    private WaitingRoom getExistingWaitingRoomWithPlayer(UUID playerUuid) {
        return uuidToWaitingRoomOpenedMap.values().stream()
                .filter(waitingRoom -> waitingRoom.getWaitingPlayerUuid().equals(playerUuid))
                .findFirst()
                .orElse(null);
    }

    private void validateWaitingRoomExistsBy(UUID waitingRoomUuid) {
        if (!uuidToWaitingRoomOpenedMap.containsKey(waitingRoomUuid)) {
            throw new IllegalArgumentException(format(ERROR_WAITING_ROOM_NOT_FOUND, waitingRoomUuid));
        }
    }

    private void validatePlayerExistsBy(UUID playerUuid) {
        if (playerUuid != null && !playerService.existBy(playerUuid)) {
            throw new IllegalArgumentException(format(ERROR_PLAYER_NOT_FOUND, playerUuid));
        }
    }

    //TODO: add Player in Waiting Room validator class
    private void validatePlayerBy(UUID playerUuid) {
        validatePlayerExistsBy(playerUuid);
        validatePlayerIsNotInAnyWaitingRoom(playerUuid);
    }

    private void validatePlayerIsNotInAnyWaitingRoom(UUID playerUuid) {
        boolean playerIsAlreadyInWaitingRoom = uuidToWaitingRoomOpenedMap.values().stream()
                .anyMatch(waitingRoom -> waitingRoom.getWaitingPlayerUuid().equals(playerUuid));
        if (playerIsAlreadyInWaitingRoom) {
            throw new IllegalArgumentException(format(ERROR_PLAYER_IS_ALREADY_IN_WAITING_ROOM, playerUuid));
        }
    }

}

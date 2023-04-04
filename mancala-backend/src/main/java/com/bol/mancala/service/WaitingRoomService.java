package com.bol.mancala.service;

import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomOutcome;
import com.bol.mancala.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
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

    /** Creates Waiting Room with the specified player and returns its uuid */
    public UUID create(UUID playerUuid) {
        validatePlayerBy(playerUuid);

        WaitingRoom waitingRoom = WaitingRoom.builder().waitingPlayerUuid(playerUuid).build();
        uuidToWaitingRoomOpenedMap.put(waitingRoom.getUuid(), waitingRoom);

        return waitingRoom.getUuid();
    }

    /** Joins an existing waiting room or creates a new one for the specified player and returns its UUID */
    public UUID joinOrCreate(UUID playerUuid) {
        validatePlayerBy(playerUuid);

        return uuidToWaitingRoomOpenedMap.values().stream()
                .findAny()
                .map(WaitingRoom::getUuid)
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
    public void close(UUID uuid, WaitingRoomOutcome outcome, UUID joinedPlayerUuid) {
        validatePlayerExistsBy(joinedPlayerUuid);

        WaitingRoom waitingRoom = uuidToWaitingRoomOpenedMap.remove(uuid);

        WaitingRoom waitingRoomClosed = waitingRoom.toBuilder()
                .outcome(outcome)
                .joinedPlayerUuid(joinedPlayerUuid)
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToWaitingRoomClosedMap.put(waitingRoomClosed.getUuid(), waitingRoomClosed);
    }

    /** Closes Waiting Room by its uuid without the joined player */
    public void close(UUID uuid, WaitingRoomOutcome outcome) {
        validateOutcomeWithoutJoinedPlayer(outcome);

        close(uuid, outcome, null);
    }

    private void validatePlayerExistsBy(UUID playerUuid) {
        if (!playerService.existBy(playerUuid)) {
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

    private static void validateOutcomeWithoutJoinedPlayer(WaitingRoomOutcome outcome) {
        if (WaitingRoomOutcome.GAME_SESSION_STARTED == outcome) {
            throw new IllegalArgumentException("joinedPlayerUuid is required");
        }
    }

}

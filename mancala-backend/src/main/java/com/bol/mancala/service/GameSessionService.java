package com.bol.mancala.service;

import com.bol.mancala.model.Board;
import com.bol.mancala.model.GameSession;
import com.bol.mancala.model.WaitingRoom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Game Session service
 */
@AllArgsConstructor
@Service
public class GameSessionService {

    private final WaitingRoomService waitingRoomService;
    private final GameInitService gameInitService;

    private final Map<UUID, GameSession> uuidToGameSessionMap = new ConcurrentHashMap<>();

    public UUID create(UUID waitingRoomUuid) {
        validateWaitingRoomExistsBy(waitingRoomUuid);

        WaitingRoom waitingRoom = waitingRoomService.getBy(waitingRoomUuid);
        validateWaitingRoom(waitingRoom);

        UUID playerOneUuid = waitingRoom.getWaitingPlayerUuid();
        UUID playerTwoUuid = waitingRoom.getJoinedPlayerUuid();
        Board board = gameInitService.initBoard(playerOneUuid, playerTwoUuid);

        GameSession gameSession = GameSession.builder()
                .playerOneUuid(playerOneUuid)
                .playerTwoUuid(playerTwoUuid)
                .board(board)
                .waitingRoomUuid(waitingRoomUuid)
                .build();

        UUID gameSessionUuid = gameSession.getUuid();
        uuidToGameSessionMap.put(gameSessionUuid, gameSession);

        return gameSessionUuid;
    }

    private void validateWaitingRoomExistsBy(UUID waitingRoomUuid) {
        if (!waitingRoomService.existsBy(waitingRoomUuid)) {
            throw new IllegalArgumentException("Waiting room was not found");
        }
    }

    private static void validateWaitingRoom(WaitingRoom waitingRoom) {
        if (waitingRoom.getJoinedPlayerUuid() == null) {
            throw new IllegalStateException("joinedPlayerUuid is required");
        }
    }

}

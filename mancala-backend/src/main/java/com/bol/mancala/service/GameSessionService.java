package com.bol.mancala.service;

import com.bol.mancala.model.GameSession;
import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomState;
import com.bol.mancala.util.LocalDateTimeUtil;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Game Session service
 */
@Validated
@AllArgsConstructor
@Service
public class GameSessionService {

    private final WaitingRoomService waitingRoomService;

    private final Map<UUID, GameSession> uuidToGameSessionMap = new ConcurrentHashMap<>();

    public GameSession createOrJoinBy(@NotNull UUID waitingRoomUuid) {
        validateWaitingRoomExistsBy(waitingRoomUuid);

        //TODO: add to waitingRoom gameSessionUuid and get it here
        GameSession gameSessionExisting = uuidToGameSessionMap.values().stream()
                .filter(gameSession -> waitingRoomUuid.equals(gameSession.getWaitingRoomUuid()))
                //TODO: add Game Session State - Active, Finished, etc.
                .filter(gameSession -> !gameSession.isLeft())
                .filter(gameSession -> gameSession.getFinishedDateTime() == null)
                .findFirst()
                .orElse(null);
        if (gameSessionExisting != null) {
            return gameSessionExisting;
        }

        WaitingRoom waitingRoom = waitingRoomService.getBy(waitingRoomUuid);
        validateWaitingRoom(waitingRoom);

        UUID playerOneUuid = waitingRoom.getWaitingPlayerUuid();
        UUID playerTwoUuid = waitingRoom.getJoinedPlayerUuid();

        GameSession gameSession = GameSession.builder()
                .playerOneUuid(playerOneUuid)
                .playerTwoUuid(playerTwoUuid)
                .waitingRoomUuid(waitingRoomUuid)
                .build();

        uuidToGameSessionMap.put(gameSession.getUuid(), gameSession);

        waitingRoomService.changeStateBy(waitingRoomUuid, WaitingRoomState.GAME_SESSION_STARTED, waitingRoom.getJoinedPlayerUuid());

        return gameSession;
    }

    public GameSession getBy(@NotNull UUID uuid) {
        return Optional.ofNullable(uuidToGameSessionMap.get(uuid))
                .orElseThrow(() -> new IllegalArgumentException("Game Session was not found"));
    }

    public boolean existBy(@NotNull UUID uuid) {
        return uuidToGameSessionMap.containsKey(uuid);
    }

    public void finishBy(@NotNull UUID uuid, GameSession gameSession) {
        validateGameSessionExistsBy(uuid);
        validateGameSessionToFinish(uuid, gameSession);

        gameSession = gameSession.toBuilder()
                .finishedDateTime(LocalDateTimeUtil.nowUtc())
                .build();

        uuidToGameSessionMap.replace(uuid, gameSession);
    }

    private void validateWaitingRoomExistsBy(@NotNull UUID uuid) {
        if (!waitingRoomService.existsBy(uuid)) {
            throw new IllegalArgumentException("Waiting Room was not found");
        }
    }

    private void validateGameSessionToFinish(@NotNull UUID uuid, @NotNull GameSession gameSession) {
        if (!uuid.equals(gameSession.getUuid())) {
            throw new IllegalArgumentException("Provided uuid differs with the Game Session uuid");
        }

        if (uuidToGameSessionMap.get(gameSession.getUuid()).getWinnerUuid() != null) {
            throw new IllegalArgumentException("The Game Session is already finished");
        }

        if (!gameSession.isLeft() && gameSession.getWinnerUuid() == null) {
            throw new IllegalArgumentException("The Game Session can't be finished if winnerUuid is not provided");
        }
    }

    private void validateGameSessionExistsBy(@NotNull UUID uuid) {
        if (!existBy(uuid)) {
            throw new IllegalArgumentException("Game Session was not found");
        }
    }

    private static void validateWaitingRoom(@NotNull WaitingRoom waitingRoom) {
        if (waitingRoom.getJoinedPlayerUuid() == null) {
            throw new IllegalStateException("joinedPlayerUuid is required");
        }
    }

}

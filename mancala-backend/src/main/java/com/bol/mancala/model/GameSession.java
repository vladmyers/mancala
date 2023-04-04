package com.bol.mancala.model;

import com.bol.mancala.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Game Session
 */
@Getter
@Builder
@AllArgsConstructor
public final class GameSession {

    @Builder.Default
    private final UUID uuid = UUID.randomUUID();

    private final UUID playerOneUuid;

    private final UUID playerTwoUuid;

    /** Which player's turn to make a move */
    @Builder.Default
    private final boolean playerOneTurn = true;

    /** Mancala Board */
    private final Board board;

    /** UUID of Player that has won, if the game session is ended; otherwise - {@code null} */
    private final UUID winnerUuid;

    /** Waiting Room UUID this game session belongs to */
    private final UUID waitingRoomUuid;

    @Builder.Default
    private final LocalDateTime createdDateTime = LocalDateTimeUtil.nowUtc();

    private final LocalDateTime finishedDateTime;

}

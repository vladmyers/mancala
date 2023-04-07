package com.bol.mancala.model;

import com.bol.mancala.util.LocalDateTimeUtil;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Game Session
 */
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public final class GameSession {

    @NotNull
    @Builder.Default
    private final UUID uuid = UUID.randomUUID();

    @NotNull
    private final UUID playerOneUuid;

    @NotNull
    private final UUID playerTwoUuid;

    /** Which player's turn to make a move */
    @NotNull
    @Builder.Default
    private final boolean playerOneTurn = true;

    /** Mancala Board */
    /*@NotNull
    private final Board board;*/

    /** UUID of Player that has won, if the game session is ended; otherwise - {@code null} */
    private final UUID winnerUuid;

    /** Indicates if game was left */
    @NotNull
    @Builder.Default
    private final boolean left = false;

    /** Indicates the player that left the game, if game was left; otherwise - {@code null} */
    private final UUID playerLeftUuid;

    /** Waiting Room UUID this game session belongs to */
    @NotNull
    private final UUID waitingRoomUuid;

    @Nullable
    @Builder.Default
    private final LocalDateTime createdDateTime = LocalDateTimeUtil.nowUtc();

    @Nullable
    private final LocalDateTime finishedDateTime;

}

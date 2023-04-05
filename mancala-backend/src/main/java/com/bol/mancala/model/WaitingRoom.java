package com.bol.mancala.model;

import com.bol.mancala.type.WaitingRoomState;
import com.bol.mancala.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Waiting Room
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@AllArgsConstructor
public final class WaitingRoom {

    @Builder.Default
    private final UUID uuid = UUID.randomUUID();

    private final UUID waitingPlayerUuid;

    /** Uuid of player that has joined the waiting room until it was closed; otherwise - {@code null} */
    private final UUID joinedPlayerUuid;

    @Builder.Default
    private final WaitingRoomState state = WaitingRoomState.OPENED;

    @Builder.Default
    private final LocalDateTime createdDateTime = LocalDateTimeUtil.nowUtc();

    private final LocalDateTime finishedDateTime;

    //TODO: add the second player and wait for each player's confirmation to start a game
    //TODO: implement statistics for average player's waiting time for the opponent

}

package com.bol.mancala.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Waiting Room outcome
 */
@Getter
@AllArgsConstructor
@Schema(description = "Waiting Room outcome", enumAsRef = true)
public enum WaitingRoomOutcome {

    GAME_SESSION_STARTED("Game session has started"),
    LEFT_BY_PLAYER("Player has left the room"),
    //TODO: implement waiting timeout
    TIMEOUT("Waiting timeout has exceeded");

    private final String description;

}

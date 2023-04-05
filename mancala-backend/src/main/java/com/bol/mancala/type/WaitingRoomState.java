package com.bol.mancala.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Waiting Room state
 */
@Getter
@AllArgsConstructor
@Schema(description = "Waiting Room state", enumAsRef = true)
public enum WaitingRoomState {

    OPENED("Waiting for another player"),
    WAITING_FOR_GAME_SESSION("Waiting for a game session to start"),
    GAME_SESSION_STARTED("Game session has started"),
    LEFT_BY_PLAYER("Player has left the room"),
    TIMEOUT("Waiting timeout has exceeded");

    private final String description;

}

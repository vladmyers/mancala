package com.bol.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Player
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public final class Player {

    private final UUID uuid = UUID.randomUUID();

    private final String username;

}

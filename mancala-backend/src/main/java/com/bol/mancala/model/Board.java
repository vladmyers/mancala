package com.bol.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Board
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public final class Board {

    @Builder.Default
    private final Set<Pit> pitSet = new HashSet<>();

}

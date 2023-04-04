package com.bol.mancala.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Pit type: regular or mancala
 */
@Getter
@AllArgsConstructor
@Schema(description = "Pit type: regular or mancala", enumAsRef = true)
public enum PitType {

    REGULAR("Regular"),
    MANCALA("Mancala");

    private final String name;

}

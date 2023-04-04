package com.bol.mancala.service;

import com.bol.mancala.model.Board;
import com.bol.mancala.model.Pit;
import com.bol.mancala.type.PitType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Game Init service
 */
@Service
@AllArgsConstructor
public class GameInitService {

    private static final int PIT_REGULAR_COUNT = 6;

    //TODO: let the players change it in the game settings
    private static final int PIT_REGULAR_STONES_COUNT = 6;

    public Board initBoard(UUID playerOneUuid, UUID playerTwoUuid) {
        Set<Pit> pitSet = new HashSet<>();
        pitSet.addAll(initPitSet(playerOneUuid));
        pitSet.addAll(initPitSet(playerTwoUuid));

        return Board.builder().pitSet(pitSet).build();
    }

    public Set<Pit> initPitSet(UUID playerUuid) {
        Set<Pit> pitSet = IntStream.range(0, PIT_REGULAR_COUNT)
                .mapToObj(i -> Pit.builder()
                        .index(i)
                        .type(PitType.REGULAR)
                        .ownerUuid(playerUuid)
                        .stones(PIT_REGULAR_STONES_COUNT)
                        .build())
                .collect(Collectors.toSet());

        pitSet.add(Pit.builder()
                .index(PIT_REGULAR_COUNT)
                .type(PitType.MANCALA)
                .ownerUuid(playerUuid)
                .stones(0)
                .build());

        return pitSet;
    }

}

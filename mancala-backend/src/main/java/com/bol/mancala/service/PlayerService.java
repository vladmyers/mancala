package com.bol.mancala.service;

import com.bol.mancala.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Player service
 */
@Service
@AllArgsConstructor
public class PlayerService {

    private final Map<UUID, Player> uuidToPlayerMap = new HashMap<>();

    public Set<Player> getAll() {
        return new HashSet<>(uuidToPlayerMap.values());
    }

    public UUID create(String username) {
        Player player = Player.builder()
                .username(username)
                .build();

        UUID playerUuid = player.getUuid();
        uuidToPlayerMap.put(playerUuid, player);

        return playerUuid;
    }

    public boolean existBy(UUID uuid) {
        return uuidToPlayerMap.containsKey(uuid);
    }

    public void deleteBy(UUID uuid) {
        uuidToPlayerMap.remove(uuid);
    }

}

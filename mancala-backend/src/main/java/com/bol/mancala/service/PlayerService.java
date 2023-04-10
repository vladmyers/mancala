package com.bol.mancala.service;

import com.bol.mancala.model.Player;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Player service
 */
@Validated
@Service
@AllArgsConstructor
public class PlayerService {

    private final Map<UUID, Player> uuidToPlayerMap = new ConcurrentHashMap<>();

    public Player create(@NotBlank String username) {
        Player player = Player.builder().username(username).build();
        uuidToPlayerMap.put(player.getUuid(), player);
        return player;
    }

    public boolean existBy(@NotNull UUID uuid) {
        return uuidToPlayerMap.containsKey(uuid);
    }

    public Player getBy(@NotNull UUID uuid) {
        return Optional.ofNullable(uuidToPlayerMap.get(uuid))
                .orElseThrow(() -> new IllegalArgumentException("Player was not found"));
    }

    public Set<Player> getAll() {
        return new HashSet<>(uuidToPlayerMap.values());
    }

    public void deleteBy(UUID uuid) {
        uuidToPlayerMap.remove(uuid);
    }

}

package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.model.Player;
import com.bol.mancala.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

import static com.bol.mancala.exception.RestResponse.success;

/**
 * Player Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(PlayerApi.URL_API_GAME_V1)
public class PlayerController implements PlayerApi {

    private final PlayerService playerService;

    public RestResponse<Set<Player>> getAll() {
        return success(playerService.getAll());
    }

    public RestResponse<Player> registerPlayer(String username) {
        return success(playerService.create(username));
    }

    public RestResponse<Player> getBy(UUID uuid) {
        return success(playerService.getBy(uuid));
    }

}

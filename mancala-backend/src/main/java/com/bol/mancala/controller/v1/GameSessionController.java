package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.bol.mancala.exception.RestResponse.success;

/**
 * Game Session Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GameSessionApi.URL_API_GAME_V1)
public class GameSessionController implements GameSessionApi {

    private final GameSessionService gameSessionService;

    public RestResponse<UUID> start(UUID waitingRoomUuid) {
        return success(gameSessionService.create(waitingRoomUuid));
    }

}

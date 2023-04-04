package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.service.WaitingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.bol.mancala.exception.RestResponse.success;

/**
 * Waiting Room Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WaitingRoomApi.URL_API_WAITING_ROOM_V1)
public class WaitingRoomController implements WaitingRoomApi {

    private final WaitingRoomService waitingRoomService;

    public RestResponse<UUID> join(UUID playerUuid) {
        return success(waitingRoomService.joinOrCreate(playerUuid));
    }

}

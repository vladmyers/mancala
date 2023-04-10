package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.service.WaitingRoomService;
import com.bol.mancala.type.WaitingRoomState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
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

    public RestResponse<WaitingRoom> join(UUID playerUuid) {
        return success(waitingRoomService.joinOrCreateFor(playerUuid));
    }

    public RestResponse<WaitingRoom> getBy(UUID uuid) {
        return success(waitingRoomService.getBy(uuid));
    }

    public RestResponse<Void> changeState(UUID uuid, WaitingRoomState waitingRoomState) {
        waitingRoomService.changeStateBy(uuid, waitingRoomState);
        return success();
    }

    public RestResponse<Set<WaitingRoom>> getAll() {
        return success(waitingRoomService.getAll());
    }

}

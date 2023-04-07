package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Waiting Room API
 */
@Tag(name = "Waiting Room", description = "Waiting Room Controller")
public interface WaitingRoomApi {

    String URL_API_WAITING_ROOM_V1 = "/api/v1/waiting-rooms";

    @Operation(summary = "Joins an existing waiting room or creates a new one and returns it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/{playerUuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<WaitingRoom> join(@Parameter(description = "Player uuid that joins waiting room", required = true)
                            @PathVariable UUID playerUuid);

    @Operation(summary = "Returns Waiting Room by its uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<WaitingRoom> get(@Parameter(description = "Waiting Room uuid", required = true)
                            @PathVariable UUID uuid);

    @Operation(summary = "Closes Waiting Room by its uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @DeleteMapping(value = "/{uuid}/{waitingRoomState}", produces = APPLICATION_JSON_VALUE)
    RestResponse<Void> close(@Parameter(description = "Waiting Room uuid", required = true)
                            @PathVariable UUID uuid,
                                    @Parameter(description = "Waiting Room state", required = true)
                                    @PathVariable WaitingRoomState waitingRoomState);

}

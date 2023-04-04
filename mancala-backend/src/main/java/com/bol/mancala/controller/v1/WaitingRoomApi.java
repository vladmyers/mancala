package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Joins an existing waiting room or creates a new one and returns its uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/{playerUuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<UUID> join(@Parameter(description = "Player uuid that joins waiting room", required = true)
                            @PathVariable UUID playerUuid);

}

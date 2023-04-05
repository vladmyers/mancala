package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.model.GameSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Game Session API
 */
@Validated
@Tag(name = "Game Session", description = "Game Session Controller")
public interface GameSessionApi {

    String URL_API_GAME_V1 = "/api/v1/game-sessions";

    @Operation(summary = "Starts new game session and returns its uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/{waitingRoomUuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<GameSession> start(@Parameter(description = "Waiting Room uuid", required = true)
               @PathVariable("waitingRoomUuid") UUID waitingRoomUuid);

    @Operation(summary = "Returns game session by its uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<GameSession> get(@Parameter(description = "Game Session uuid", required = true)
               @PathVariable("uuid") UUID uuid);

    @Operation(summary = "Finishes game session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    RestResponse<Void> finish(
            @Parameter(description = "Game Session uuid", required = true)
            @PathVariable("uuid") UUID uuid,
            @Parameter(description = "Game Session", required = true)
            @RequestBody @Valid GameSession gameSession);

}

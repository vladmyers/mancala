package com.bol.mancala.controller.v1;

import com.bol.mancala.exception.RestResponse;
import com.bol.mancala.model.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Game API
 */
@Tag(name = "Player", description = "Player Controller")
public interface PlayerApi {

    String URL_API_GAME_V1 = "/api/v1/players";

    @Operation(summary = "Returns all players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    RestResponse<Set<Player>> getAll();

    @Operation(summary = "Registers new player and returns his uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/{username}", produces = APPLICATION_JSON_VALUE)
    RestResponse<UUID> registerPlayer(@Parameter(description = "Username", required = true)
                        @PathVariable("username") String username);

}

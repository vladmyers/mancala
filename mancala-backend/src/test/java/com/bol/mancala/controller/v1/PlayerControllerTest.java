package com.bol.mancala.controller.v1;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link PlayerController}
 */
@AutoConfigureMockMvc
@SpringBootTest
class PlayerControllerTest {

    private static final String URI = PlayerApi.URL_API_PLAYER_V1;

    @Autowired
    private MockMvc mockMvc;

    /** Tests Player is registered and returned */
    @SneakyThrows
    @Test
    void registerPlayer() {
        mockMvc.perform(MockMvcRequestBuilders.post(URI + "/player")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.uuid").exists())
                .andExpect(jsonPath("$.result.username").value("player"));
    }

    /** Tests Player was not found by uuid */
    @SneakyThrows
    @Test
    void getByTestNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message").value("Player was not found"))
                .andExpect(jsonPath("$.error.displayMessage").value("Validation has failed"));
    }

    /** Tests Player set is returned when obtaining all players (empty set) */
    @SneakyThrows
    @Test
    void getAll() {
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result").isEmpty());
    }

}
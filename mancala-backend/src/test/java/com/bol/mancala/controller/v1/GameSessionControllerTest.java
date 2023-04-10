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
 * Tests for {@link GameSessionController}
 */
@AutoConfigureMockMvc
@SpringBootTest
class GameSessionControllerTest {

    private static final String URI = GameSessionApi.URL_API_GAME_V1;

    @Autowired
    private MockMvc mockMvc;

    /** Tests Waiting Room was not found by uuid when trying to start new Game Session */
    @SneakyThrows
    @Test
    void startByTestNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.post(URI + "/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message").value("Waiting Room was not found"))
                .andExpect(jsonPath("$.error.displayMessage").value("Validation has failed"));
    }

    /** Tests Game Session was not found by uuid */
    @SneakyThrows
    @Test
    void getByTestNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message").value("Game Session was not found"))
                .andExpect(jsonPath("$.error.displayMessage").value("Validation has failed"));
    }

    /** Tests Game Session was not found by uuid when trying to finish it */
    @SneakyThrows
    @Test
    void finishByTestNotFound() {
        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message").value("Game Session was not found"))
                .andExpect(jsonPath("$.error.displayMessage").value("Validation has failed"));
    }

}
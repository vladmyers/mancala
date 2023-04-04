package com.bol.mancala.exception;

import com.bol.mancala.controller.PlayerApi;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    private static final String URI = PlayerApi.URL_API_GAME_V1;

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void handleHttpRequestMethodNotSupported() {
        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.displayMessage").value(ErrorMessage.CONTROLLER_HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage()))
                .andExpect(jsonPath("$.error.message").value("Request method 'POST' is not supported"));
    }

    //TODO: tests for the rest exception handlers

}

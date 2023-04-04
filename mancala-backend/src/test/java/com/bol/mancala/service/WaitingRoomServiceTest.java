package com.bol.mancala.service;

import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomOutcome;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class WaitingRoomServiceTest {

    private static final UUID uuidNonExistent = UUID.nameUUIDFromBytes("non-existent-uuid".getBytes());

    @Autowired
    private WaitingRoomService waitingRoomService;

    @Autowired
    private PlayerService playerService;

    private UUID playerUuid1, playerUuid2, playerUuid3;
    private UUID waitingRoomUuid1, waitingRoomUuid2;

    @BeforeAll
    void beforeAll() {
        playerUuid1 = playerService.create("user1");
        playerUuid2 = playerService.create("user2");
        playerUuid3 = playerService.create("user3");
        waitingRoomUuid1 = waitingRoomService.create(playerUuid2);
        waitingRoomUuid2 = waitingRoomService.create(playerUuid3);
    }

    @AfterAll
    void afterAll() {
        playerService.deleteBy(playerUuid1);
        playerService.deleteBy(playerUuid2);
        playerService.deleteBy(playerUuid3);
        waitingRoomService.close(waitingRoomUuid1, WaitingRoomOutcome.LEFT_BY_PLAYER);
    }

    @Test
    void create() {
        UUID uuid = waitingRoomService.create(playerUuid1);

        assertNotNull(uuid);

        WaitingRoom created = waitingRoomService.getBy(uuid);

        assertNotNull(created);
        assertNotNull(created.getCreatedDateTime());
        assertNotNull(created.getWaitingPlayerUuid());
    }

    @Test
    void createTestPlayerUuidNotFound() {
        var exception = assertThrows(IllegalArgumentException.class, () -> waitingRoomService.create(uuidNonExistent));
        assertEquals("Player was not found by uuid=" + uuidNonExistent, exception.getMessage());
    }

    @Test
    void createTestPlayerIsAlreadyInWaitingRoom() {
        var exception = assertThrows(IllegalArgumentException.class, () -> waitingRoomService.create(playerUuid2));
        assertEquals("Player with uuid=" + playerUuid2 + " is already in a waiting room", exception.getMessage());
    }

    @Test
    void existsBy() {
        assertTrue(waitingRoomService.existsBy(waitingRoomUuid1));
    }

    @Test
    void existsByTestFalse() {
        assertFalse(waitingRoomService.existsBy(uuidNonExistent));
    }

    @Test
    void getBy() {
        WaitingRoom waitingRoom = waitingRoomService.getBy(waitingRoomUuid1);

        assertNotNull(waitingRoom);
    }

    @Test
    void getByTestNotFound() {
        var exception = assertThrows(IllegalArgumentException.class, () -> waitingRoomService.getBy(uuidNonExistent));
        assertEquals("Waiting room was not found by uuid=" + uuidNonExistent, exception.getMessage());
    }

    @Test
    void closeBy() {
        waitingRoomService.close(waitingRoomUuid2, WaitingRoomOutcome.LEFT_BY_PLAYER);

        assertFalse(waitingRoomService.existsBy(waitingRoomUuid2));
    }

    @Test
    void closeByTestNotFound() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> waitingRoomService.close(uuidNonExistent, WaitingRoomOutcome.LEFT_BY_PLAYER));
        assertEquals("Waiting room was not found by uuid=" + uuidNonExistent, exception.getMessage());
    }

}

package com.bol.mancala.service;

import com.bol.mancala.model.WaitingRoom;
import com.bol.mancala.type.WaitingRoomState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link WaitingRoomService}
 */
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

    /** Adds test objects */
    @BeforeAll
    void beforeAll() {
        playerUuid1 = playerService.create("user1").getUuid();
        playerUuid2 = playerService.create("user2").getUuid();
        playerUuid3 = playerService.create("user3").getUuid();
        waitingRoomUuid1 = waitingRoomService.createFor(playerUuid2).getUuid();
        waitingRoomUuid2 = waitingRoomService.createFor(playerUuid3).getUuid();
    }

    /** Cleanup after tests */
    @AfterAll
    void afterAll() {
        playerService.deleteBy(playerUuid1);
        playerService.deleteBy(playerUuid2);
        playerService.deleteBy(playerUuid3);
        waitingRoomService.changeStateBy(waitingRoomUuid1, WaitingRoomState.LEFT_BY_PLAYER);
    }

    @Test
    void createFor() {
        WaitingRoom created = waitingRoomService.createFor(playerUuid1);

        assertNotNull(created);

        assertNotNull(created.getCreatedDateTime());
        assertNull(created.getFinishedDateTime());

        assertNotNull(created.getWaitingPlayerUuid());
        assertNull(created.getJoinedPlayerUuid());

        assertEquals(WaitingRoomState.OPENED, created.getState());
    }

    @Test
    void createForTestPlayerUuidNotFound() {
        var exception = assertThrows(IllegalArgumentException.class, () -> waitingRoomService.createFor(uuidNonExistent));
        assertEquals("Player was not found by uuid=" + uuidNonExistent, exception.getMessage());
    }

    @Test
    void createForTestPlayerIsAlreadyInWaitingRoom() {
        var exception = assertThrows(IllegalArgumentException.class, () -> waitingRoomService.createFor(playerUuid2));
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
        waitingRoomService.changeStateBy(waitingRoomUuid2, WaitingRoomState.LEFT_BY_PLAYER);

        WaitingRoom closedWaitingRoom = waitingRoomService.getBy(waitingRoomUuid2);

        assertEquals(WaitingRoomState.LEFT_BY_PLAYER, closedWaitingRoom.getState());
        assertNotNull(closedWaitingRoom.getFinishedDateTime());
        assertThat(closedWaitingRoom.getFinishedDateTime()).isCloseToUtcNow(within(1, ChronoUnit.SECONDS));
    }

    @Test
    void closeByTestJoined() {
        waitingRoomService.changeStateBy(waitingRoomUuid2, WaitingRoomState.WAITING_FOR_GAME_SESSION, playerUuid1);

        WaitingRoom closedWaitingRoom = waitingRoomService.getBy(waitingRoomUuid2);

        assertEquals(WaitingRoomState.WAITING_FOR_GAME_SESSION, closedWaitingRoom.getState());
        assertNotNull(closedWaitingRoom.getFinishedDateTime());
        assertThat(closedWaitingRoom.getFinishedDateTime()).isCloseToUtcNow(within(1, ChronoUnit.SECONDS));
    }

    @Test
    void closeByTestNotFound() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> waitingRoomService.changeStateBy(uuidNonExistent, WaitingRoomState.LEFT_BY_PLAYER));
        assertEquals("Waiting room was not found by uuid=" + uuidNonExistent, exception.getMessage());
    }

}

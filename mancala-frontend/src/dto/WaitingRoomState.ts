/**
 * Waiting Room outcome
 */
enum WaitingRoomState {

    OPENED = "Waiting for another player",
    WAITING_FOR_GAME_SESSION = "Waiting for a game session to start",
    GAME_SESSION_STARTED = "Game session has started",
    LEFT_BY_PLAYER = "Player has left the room",
    //TODO: implement waiting timeout
    TIMEOUT = "Waiting timeout has exceeded"

}

export default WaitingRoomState;
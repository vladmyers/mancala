/**
 * Waiting Room State
 */
export class WaitingRoomState {

    static values: WaitingRoomState[] = [];
    static readonly OPENED = new WaitingRoomState("OPENED", "Waiting for another player",);
    static readonly WAITING_FOR_GAME_SESSION = new WaitingRoomState("WAITING_FOR_GAME_SESSION", "Waiting for a game session to start");
    static readonly GAME_SESSION_STARTED = new WaitingRoomState("GAME_SESSION_STARTED", "Game session has started");
    static readonly LEFT_BY_PLAYER = new WaitingRoomState("LEFT_BY_PLAYER", "Player has left the room");
    static readonly TIMEOUT = new WaitingRoomState("TIMEOUT", "Waiting timeout has exceeded");

    private constructor(readonly name:string, readonly displayValue: string) {
        WaitingRoomState.values.push(this);
    }

    static getByName(name: string | undefined): WaitingRoomState | undefined {
        return this.values.find((WaitingRoomState: WaitingRoomState) => WaitingRoomState.name === name);
    }

}

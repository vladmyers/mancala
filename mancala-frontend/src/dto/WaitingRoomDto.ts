import WaitingRoomState from "./type/WaitingRoomState";

/**
 * Waiting Room
 */
export interface WaitingRoomDto {
    uuid: string;
    waitingPlayerUuid: string;
    joinedPlayerUuid?: string;
    state: WaitingRoomState;
    createdDateTime: Date;
    finishedDateTime?: Date;
}
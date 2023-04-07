/**
 * Waiting Room
 */
export interface WaitingRoomDto {
    uuid: string;
    waitingPlayerUuid: string;
    joinedPlayerUuid?: string;
    state: string;
    createdDateTime: Date;
    finishedDateTime?: Date;
}
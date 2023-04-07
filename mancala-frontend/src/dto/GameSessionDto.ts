/** Game Session */
export interface GameSessionDto {
    uuid: string;
    playerOneUuid: string;
    playerTwoUuid: string;
    playerOneTurn: boolean;
    winnerUuid?: string;
    left: boolean;
    playerLeftUuid: string;
    waitingRoomUuid: string;
    createdDateTime: string;
    finishedDateTime?: string;
}

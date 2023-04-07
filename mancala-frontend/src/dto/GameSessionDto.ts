/** Game Session */
export interface GameSessionDto {
    uuid: string;
    playerOneUuid: string;
    playerTwoUuid: string;
    playerOneTurn: boolean;
    //board: BoardDto;
    winnerUuid?: string;
    left: boolean;
    playerLeftUuid: string;
    waitingRoomUuid: string;
    createdDateTime: string;
    finishedDateTime?: string;
}

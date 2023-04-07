import React, {useState} from 'react';
import {Alert, Button, Row} from 'react-bootstrap';
import './MancalaGame.css';
import {GameSessionDto} from "../dto/GameSessionDto";

type GameState = {
    pits: number[];
    player1Turn: boolean;
};

type MancalaBoardProps = {
    gameSession?: GameSessionDto;
    onGameSessionUpdate?: (updatedSession: GameSessionDto) => void;
};

const NUM_PITS_PER_PLAYER = 6;
const INITIAL_STONES_PER_PIT = 6;
const NUM_TOTAL_PITS = NUM_PITS_PER_PLAYER * 2 + 2;
const PLAYER1_PIT_INDEX = NUM_PITS_PER_PLAYER;
const PLAYER2_PIT_INDEX = NUM_TOTAL_PITS - 1;

const MancalaGame: React.FC<MancalaBoardProps> = ({
                                                      gameSession,
                                                      onGameSessionUpdate,
                                                  }) => {

    const initState: GameState = {
        pits: Array(NUM_TOTAL_PITS).fill(INITIAL_STONES_PER_PIT),
        player1Turn: true,
    };

    const [gameState, setGameState] = useState(initState);
    const [currentPlayer, setCurrentPlayer] = useState<number>(1);
    const [gameOver, setGameOver] = useState<boolean>(false);
    /** 0: Tie, 1: Player 1, 2: Player 2 */
    const [winner, setWinner] = useState<number | null>(null);
    const [winnerMessage, setWinnerMessage] = useState<string | null>(null);

    const getOpponentPitIndex = (pitIndex: number): number => {
        return NUM_TOTAL_PITS - 1 - pitIndex;
    };

    const isPlayer1Pit = (pitIndex: number): boolean => {
        return pitIndex < PLAYER1_PIT_INDEX;
    };

    const isPlayer2Pit = (pitIndex: number): boolean => {
        return pitIndex > PLAYER1_PIT_INDEX && pitIndex < PLAYER2_PIT_INDEX;
    }

    const getMancalaIndex = (player1: boolean): number => {
        return player1 ? PLAYER1_PIT_INDEX : PLAYER2_PIT_INDEX;
    };

    const isPlayer1Mancala = (pitIndex: number): boolean => {
        return pitIndex === PLAYER1_PIT_INDEX;
    };

    const isPlayer2Mancala = (pitIndex: number): boolean => {
        return pitIndex === PLAYER2_PIT_INDEX;
    };

    const getPlayer1Score = (): number => {
        return gameState.pits[PLAYER1_PIT_INDEX];
    };

    const getPlayer2Score = (): number => {
        return gameState.pits[PLAYER2_PIT_INDEX];
    };

    const resetGame = (): void => {
        setGameState(initState);
        setGameOver(false);
        setWinner(null);
        setCurrentPlayer(1);
    };

    const endGame = (): void => {
        const player1Score = getPlayer1Score();
        const player2Score = getPlayer2Score();
        if (player1Score === player2Score) {
            setWinner(0);
            setWinnerMessage("It's a tie!");
        } else {
            setWinner(player1Score > player2Score ? 1 : 2);
            setWinnerMessage(`Player ${player1Score > player2Score ? 1 : 2} wins!`);
        }
        setGameOver(true);
    };

    const isPitEmpty = (pitIndex: number): boolean => {
        return gameState.pits[pitIndex] === 0;
    };

    const isGameOver = (): boolean => {
        const player1Empty = gameState.pits.slice(0, PLAYER1_PIT_INDEX).every(stones => stones === 0) && !isPitEmpty(PLAYER1_PIT_INDEX);
        const player2Empty = gameState.pits.slice(PLAYER1_PIT_INDEX + 1, PLAYER2_PIT_INDEX).every(stones => stones === 0) && !isPitEmpty(PLAYER2_PIT_INDEX);
        return player1Empty || player2Empty;
    };

    const handlePitClick = (pitIndex: number): void => {
        //Check if the pit belongs to the current player
        if ((gameState.player1Turn && isPlayer2Pit(pitIndex)) ||
            (!gameState.player1Turn && isPlayer1Pit(pitIndex))) {
            return;
        }

        //Check if the pit is empty
        if (isPitEmpty(pitIndex)) {
            return;
        }

        let newGameState: GameState = {
            ...gameState,
        };

        let stonesInHand = newGameState.pits[pitIndex];
        newGameState.pits[pitIndex] = 0;

        let currentPitIndex = pitIndex;

        while (stonesInHand > 0) {
            currentPitIndex++;
            if (currentPitIndex >= NUM_TOTAL_PITS) {
                currentPitIndex = 0;
            }

            // Skip the opponent's mancala
            if ((gameState.player1Turn && isPlayer2Mancala(currentPitIndex)) ||
                (!gameState.player1Turn && isPlayer1Mancala(currentPitIndex))) {
                continue;
            }

            newGameState.pits[currentPitIndex]++;
            stonesInHand--;

            //If the last stone landed in an empty pit owned by the current player,
            //capture the stones from the opposite pit and add them to the player's mancala
            if (stonesInHand === 0 && newGameState.pits[currentPitIndex] === 1) {
                const opponentPitIndex = getOpponentPitIndex(currentPitIndex);
                const capturedStones = newGameState.pits[opponentPitIndex];

                newGameState.pits[opponentPitIndex] = 0;
                newGameState.pits[currentPitIndex] = 0;
                newGameState.pits[getMancalaIndex(gameState.player1Turn)] += capturedStones + 1;
            }
        }

        //Switch turn if the last stone was not placed in the mancala
        if (isPlayer1Mancala(currentPitIndex) && gameState.player1Turn) {
            setCurrentPlayer(2);
        } else if (isPlayer2Mancala(currentPitIndex) && !gameState.player1Turn) {
            setCurrentPlayer(1);
        } else {
            newGameState.player1Turn = !gameState.player1Turn;
            setCurrentPlayer(gameState.player1Turn ? 2 : 1);
        }

        setGameState(() => {
            return newGameState
        });

        //Check if the game is over
        if (isGameOver()) {
            endGame();
        }
    };

    return (
        <div style={{marginTop: 20}}>
            {!gameOver && (
                <Row className="justify-content-md-center mb-3">
                    <div className="text-center text-black">
                        {`Player ${currentPlayer}'s turn`}
                    </div>
                </Row>)
            }
            {gameOver && (
                <div className="text-center">
                    {winnerMessage && (
                        <Alert variant={winner === 0 ? "info" : "success"} className="mt-4">
                            {winnerMessage}
                        </Alert>
                    )}
                    <Button className="small text-white" onClick={resetGame} variant="dark" style={{marginBottom: 30}}>Play Again</Button>
                </div>
            )}
            <div className="mancala-container">
                <div className="mancala-board bg-secondary p-3">
                    <div className="pit-row">
                        <div className="player2-mancala text-center">
                            <h5>Player 2</h5>
                            <div className="mancala-pit">{getPlayer2Score()}</div>
                            <div className="mancala-label">Mancala</div>
                        </div>
                        {[...Array(NUM_PITS_PER_PLAYER)].map((_, i) => (
                            <div key={i} className={`pit player2-pit${i} text-center`}>
                                <div
                                    className="pit bg-white d-inline-block m-1"
                                    onClick={() => handlePitClick(PLAYER2_PIT_INDEX - i - 1)}
                                >
                                    {gameState.pits[PLAYER2_PIT_INDEX - i - 1]}
                                </div>
                                <div className="pit-label">Pit {NUM_PITS_PER_PLAYER - i}</div>
                                <Button onClick={() => handlePitClick(PLAYER2_PIT_INDEX - i - 1)}
                                        disabled={gameState.player1Turn || gameState.pits[PLAYER2_PIT_INDEX - i - 1] === 0 || gameOver}
                                        className="btn btn-primary">
                                    Select
                                </Button>
                            </div>
                        ))}
                    </div>
                    <div className="spacer"/>
                    <div className="pit-row">
                        {[...Array(NUM_PITS_PER_PLAYER)].map((_, i) => (
                            <div key={i} className={`pit player1-pit${i} text-center`}>
                                <div
                                    className="pit bg-white d-inline-block m-1"
                                    onClick={() => handlePitClick(i)}
                                >
                                    {gameState.pits[i]}
                                </div>
                                <div className="pit-label">Pit {i + 1}</div>
                                <Button onClick={() => handlePitClick(i)}
                                        disabled={!gameState.player1Turn || gameState.pits[i] === 0 || gameOver}
                                        className="btn btn-primary">
                                    Select
                                </Button>
                            </div>
                        ))}
                        <div className="player1-mancala text-center">
                            <h5>Player 1</h5>
                            <div className="mancala-pit">{getPlayer1Score()}</div>
                            <div className="mancala-label">Mancala</div>
                        </div>
                    </div>
                    <Row className="justify-content-center text-center">
                        <Button className="small text-white" onClick={resetGame} variant="dark">
                            Reset
                        </Button>
                    </Row>
                </div>
            </div>
        </div>
    );

};

export default MancalaGame;

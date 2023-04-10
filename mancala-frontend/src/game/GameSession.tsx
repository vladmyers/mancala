import React from 'react';
import {useNavigate} from 'react-router-dom';
import {ServiceLocator} from '../service/ServiceLocator';
import GameSessionService from '../service/GameSessionService';
import {AxiosError, AxiosResponse} from 'axios';
import {RestResponseError} from '../api/RestResponseError';
import {RestResponse} from '../api/RestResponse';
import {GameSessionDto} from "../dto/GameSessionDto";

import Header from '../component/Header';
import Body from '../component/Body';
import MancalaGame from "./MancalaGame";

import "./GameSession.css"

const GameSession = () => {
    const navigate = useNavigate();
    const gameSessionService: GameSessionService = ServiceLocator.get('gameSessionService')!;

    const [errorMessage, setErrorMessage] = React.useState('');
    const [gameSession, setGameSession] = React.useState<GameSessionDto>();
    const [intervalId, setIntervalId] = React.useState<NodeJS.Timer>();

    React.useEffect(() => {
        const gameSessionUuid = sessionStorage.getItem('gameSessionUuid');
        if (gameSessionUuid) {
            gameSessionService.getBy(gameSessionUuid).then((response) => {
                setGameSession(() => {return response.result});
            });
            return;
        }

        const waitingRoomUuid = sessionStorage.getItem('waitingRoomUuid');
        if (!waitingRoomUuid) {
            navigate('/waiting-room');
            return;
        }

        gameSessionService
            .startBy(waitingRoomUuid)
            .then((response: RestResponse<GameSessionDto>) => {
                if (response.result) {
                    setGameSession(() => {
                        return response.result
                    });
                    sessionStorage.removeItem('waitingRoomUuid');
                }
            })
            .catch((e: AxiosError) => {
                let axiosResponse = e?.response as AxiosResponse<RestResponseError>;
                if (axiosResponse?.data) {
                    let restResponse = axiosResponse?.data as RestResponse<any>;
                    let restResponseError = restResponse.error!;
                    setErrorMessage(restResponseError.displayMessage + ': ' + restResponseError.message);
                }
            });
    }, []);

    React.useEffect(() => {
        if (gameSession?.uuid && !intervalId) {
            pollForGameSessionState();
        }
    }, [gameSession]);

    React.useEffect(() => {
        if (gameSession?.left && gameSession.playerLeftUuid != sessionStorage.getItem('playerUuid')) {
            clearInterval(intervalId);
            sessionStorage.removeItem('gameSessionUuid');
            alert('Another player has left the game');
        }
    }, [gameSession?.left]);

    React.useEffect(() => {
        if (gameSession?.uuid) sessionStorage.setItem('gameSessionUuid', gameSession.uuid);
    }, [gameSession?.uuid]);

    const pollForGameSessionState = () => {
        const id = setInterval(() => {
            if (gameSession?.uuid) {
                gameSessionService.getBy(gameSession.uuid).then((response) => {
                    setGameSession(() => {
                        return response.result
                    });
                });
            }
        }, 1000);
        setIntervalId(() => {
            return id
        });

        return () => clearInterval(intervalId);
    };

    const handleLeaveGame = () => {
        if (gameSession) {
            gameSession.left = true
            gameSession.playerLeftUuid = sessionStorage.getItem('playerUuid')!;
            gameSessionService.finishBy(gameSession?.uuid, gameSession);
            setGameSession(() => {return undefined});
            clearInterval(intervalId);
            sessionStorage.removeItem('gameSessionUuid');
            navigate("/");
        }
    };

    return (
        <>
            <Header gameSessionUuid={gameSession?.uuid} onLeaveGame={() => handleLeaveGame()}/>
            <Body>
                <div>
                    <div className="col" style={{position: 'relative'}}>
                        <h1>Game Session</h1>
                        <div className="beta-sticker">Beta</div>
                    </div>
                    <div>This is a Beta version, and currently, the game session is not being synchronized between connected players.
                        Please take turns one by one on the same open page for now</div>
                </div>
                <MancalaGame gameSession={gameSession}/>
                <div className="d-flex justify-content-left align-items-center" style={{minHeight: '200px'}}>
                    {!errorMessage && gameSession && (
                        <div>
                            <div>Game session UUID: {gameSession.uuid}</div>
                            <div>Created date: {new Date(gameSession.createdDateTime).toLocaleString(navigator.language)}</div>
                            <div>Player 1: {gameSession.playerOneUuid}</div>
                            <div>Player 2: {gameSession.playerTwoUuid}</div>
                        </div>
                    )}
                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}
                </div>
            </Body>
        </>
    );
};

export default GameSession;

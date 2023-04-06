import React from 'react';
import {useNavigate} from 'react-router-dom';
import {ServiceLocator} from '../service/ServiceLocator';
import GameSessionService from '../service/GameSessionService';
import Header from '../component/Header';
import Body from '../component/Body';
import {AxiosError, AxiosResponse} from 'axios';
import {RestResponseError} from '../api/RestResponseError';
import {RestResponse} from '../api/RestResponse';
import {GameSessionDto} from "../dto/GameSessionDto";

const GameSession = () => {
    const navigate = useNavigate();
    const gameSessionService: GameSessionService = ServiceLocator.get('gameSessionService')!;

    const [errorMessage, setErrorMessage] = React.useState('');
    const [gameSession, setGameSession] = React.useState<GameSessionDto>();
    const [waitingRoomUuid, setWaitingRoomUuid] = React.useState<string>();

    React.useEffect(() => {
        const waitingRoomUuid = sessionStorage.getItem('waitingRoomUuid');
        if (waitingRoomUuid) {
            setWaitingRoomUuid(() => {return waitingRoomUuid});
            sessionStorage.removeItem('waitingRoomUuid');
        } else {
            navigate('/waiting-room');
        }

        gameSessionService
            .create(waitingRoomUuid!)
            .then((response: RestResponse<GameSessionDto>) => {
                if (response.result) {
                    setGameSession(response.result);
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

    const handleLeaveGame = () => {
        if (gameSession) {
            gameSessionService.finish(gameSession?.uuid, gameSession).then(() => navigate("/"));
        }
    };

    return (
        <>
            <Header gameSessionUuid={gameSession?.uuid} onLeaveGame={() => handleLeaveGame()}/>
            <Body>
                <h1>Game Session</h1>
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

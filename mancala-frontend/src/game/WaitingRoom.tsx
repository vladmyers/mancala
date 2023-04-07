import React from 'react';
import {useNavigate} from 'react-router-dom';
import {ServiceLocator} from '../service/ServiceLocator';
import WaitingRoomService from '../service/WaitingRoomService';
import {AxiosError, AxiosResponse} from 'axios';
import {RestResponseError} from "../api/RestResponseError";
import {RestResponse} from "../api/RestResponse";
import {WaitingRoomDto} from "../dto/WaitingRoomDto";
import {WaitingRoomState} from "../dto/type/WaitingRoomState";

import Header from '../component/Header';
import Body from '../component/Body';

const WaitingRoom = () => {
    const navigate = useNavigate();
    const waitingRoomService: WaitingRoomService = ServiceLocator.get('waitingRoomService')!;

    const [errorMessage, setErrorMessage] = React.useState('');
    const [waitingRoomDto, setWaitingRoomDto] = React.useState<WaitingRoomDto>();
    const [intervalId, setIntervalId] = React.useState<NodeJS.Timer>();

    React.useEffect(() => {
        const playerUuid = sessionStorage.getItem('playerUuid');
        const playerUsername = sessionStorage.getItem('playerUsername');
        if (!playerUuid || !playerUsername) {
            navigate('/register');
            return;
        }

        waitingRoomService.join(playerUuid).then((response) => {
            if (response.result) setWaitingRoomDto(() => {return response.result});
        }).catch((e: AxiosError) => {
            let axiosResponse = e?.response as AxiosResponse<RestResponseError>;
            if (axiosResponse?.data) {
                let restResponse = axiosResponse?.data as RestResponse<any>;
                let restResponseError = restResponse.error!;
                setErrorMessage(restResponseError.displayMessage + ": " + restResponseError.message);
            }
        });
    }, []);

    React.useEffect(() => {
        if (waitingRoomDto?.waitingPlayerUuid && waitingRoomDto?.joinedPlayerUuid) {
            if (intervalId) clearInterval(intervalId);
            sessionStorage.setItem('waitingRoomUuid', waitingRoomDto.uuid);
            navigate('/game-session');
        } else if (waitingRoomDto?.uuid && !intervalId) {
            pollForGameStart();
        }
    }, [waitingRoomDto]);

    const pollForGameStart = () => {
        const id = setInterval(() => {
            if (waitingRoomDto?.uuid) {
                waitingRoomService.get(waitingRoomDto.uuid).then((response) => {
                    setWaitingRoomDto(() => {return response.result});
                });
            }
        }, 1000);
        setIntervalId(() => {return id});

        return () => clearInterval(intervalId);
    };

    const handleLeaveRoom = () => {
        //TODO: process response
        waitingRoomService.leave(waitingRoomDto?.uuid!);
        setWaitingRoomDto(() => {return undefined});
        clearInterval(intervalId);
        navigate('/');
    };

    return (
        <>
            <Header waitingRoomUuid={waitingRoomDto?.uuid} onLeaveRoom={() => handleLeaveRoom()}/>
            <Body>
                <h1>Waiting Room</h1>
                <div>#{waitingRoomDto?.uuid}</div>
                <div>State: {WaitingRoomState.getByName(waitingRoomDto?.state)?.displayValue}</div>
                <div>Created: {new Date(waitingRoomDto?.createdDateTime!).toLocaleString(navigator.language)}</div>
                <div className="d-flex justify-content-left align-items-center" style={{minHeight: '100px'}}>
                    {!errorMessage && (
                        <div className="d-flex align-items-center">
                            <div style={{marginRight: "10px"}}>Waiting for another player to join...</div>
                            <div className="spinner-border" role="status"/>
                        </div>
                    )}
                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}
                </div>
                <div className="d-flex justify-content-left">
                    <button className="btn btn-primary"
                        //TODO: add all styles to css files
                            style={{backgroundColor: '#343a40', borderColor: '#343a40', marginTop: '30px'}}
                            onClick={handleLeaveRoom}>Leave Room
                    </button>
                </div>
            </Body>
        </>
    );
};

export default WaitingRoom;

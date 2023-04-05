import React from 'react';
import { useNavigate } from 'react-router-dom';
import { ServiceLocator } from '../service/ServiceLocator';
import WaitingRoomService from '../service/WaitingRoomService';
import Header from '../component/Header';
import Body from '../component/Body';
import { AxiosError, AxiosResponse } from 'axios';
import {RestResponseError} from "../api/RestResponseError";
import {RestResponse} from "../api/RestResponse";
import {WaitingRoomDto} from "../dto/WaitingRoomDto";

const WaitingRoom = () => {const navigate = useNavigate();
    const waitingRoomService: WaitingRoomService = ServiceLocator.get('waitingRoomService')!;

    const [errorMessage, setErrorMessage] = React.useState('');
    const [waitingRoomDto, setWaitingRoomDto] = React.useState<WaitingRoomDto>();
    const [isLeft, setIsLeft] = React.useState(false);

    React.useEffect(() => {
        const playerUuid = sessionStorage.getItem('playerUuid');
        const playerUsername = sessionStorage.getItem('playerUsername');
        if (!playerUuid || !playerUsername) {
            navigate('/register');
            return;
        }

        waitingRoomService.join(playerUuid).then((response) => {
            if (response.result) {
                setWaitingRoomDto(response.result);
                if (waitingRoomDto?.waitingPlayerUuid && waitingRoomDto?.joinedPlayerUuid) {
                    navigate('/game-session');
                } else {
                    pollForGameStart();
                }
            }
        }).catch((e: AxiosError) => {
            let axiosResponse = e?.response as AxiosResponse<RestResponseError>;
            if (axiosResponse?.data) {
                let restResponse = axiosResponse?.data as RestResponse<any>;
                let restResponseError = restResponse.error!;
                setErrorMessage(restResponseError.displayMessage + ": " + restResponseError.message);
            }
        });
    }, []);

    const pollForGameStart = () => {
        const intervalId = setInterval(() => {
            if (waitingRoomDto?.uuid) {
                waitingRoomService.get(waitingRoomDto?.uuid).then((response) => {
                    setWaitingRoomDto(response.result);
                    if (waitingRoomDto?.joinedPlayerUuid || isLeft) {
                        clearInterval(intervalId);
                        if (!isLeft) navigate('/game-session');
                    }
                });
            }
        }, 5000);
    };

    const handleLeaveRoom = () => {
        waitingRoomService.leave(waitingRoomDto?.uuid!).then(() => {
            setIsLeft(true);
            navigate('/');
        });
    };

    return (
        <>
            <Header/>
            <Body>
                <h1>Waiting Room</h1>
                <div>#{waitingRoomDto?.uuid}</div>
                <div>{waitingRoomDto?.state}</div>
                <div>{new Date(waitingRoomDto?.createdDateTime!).toLocaleString()}</div>
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
                            style={{ backgroundColor: '#343a40', borderColor: '#343a40', marginTop: '30px' }}
                            onClick={handleLeaveRoom}>Leave Room</button>
                </div>
            </Body>
        </>
    );
};

export default WaitingRoom;

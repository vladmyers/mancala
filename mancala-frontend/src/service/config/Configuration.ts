import {ServiceLocator} from "../ServiceLocator";
import PlayerService from "../PlayerService";
import WaitingRoomService from "../WaitingRoomService";
import GameSessionService from "../GameSessionService";

export const _API_BASE_URL = "http://localhost:8080";

const configure = () => {
    ServiceLocator
        .add("playerService", new PlayerService())
        .add("waitingRoomService", new WaitingRoomService())
        .add("gameSessionService", new GameSessionService())
    ;
}

export default configure;

import {ServiceLocator} from "../ServiceLocator";
import PlayerService from "../PlayerService";

export const _API_BASE_URL = "http://localhost:8080";

const configure = () => {
    ServiceLocator
        .add("playerService", new PlayerService())
    ;
}

export default configure;

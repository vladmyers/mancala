import {getResource, postData, putData} from "../util/axiosApi";
import {RestResponse} from "../api/RestResponse";
import {GameSessionDto} from "../dto/GameSessionDto";

/**
 * Game Session service
 */
export default class GameSessionService {

    static URI_V1 = "/api/v1/game-sessions";

    startBy(waitingRoomUuid: string): Promise<RestResponse<GameSessionDto>> {
        return postData(`${GameSessionService.URI_V1}/${waitingRoomUuid}`);
    }

    getBy(uuid: string): Promise<RestResponse<GameSessionDto>> {
        return getResource(`${GameSessionService.URI_V1}/${uuid}`)
    }

    finishBy(uuid: string, gameSessionDto: GameSessionDto): Promise<RestResponse<void>> {
        return putData(`${GameSessionService.URI_V1}/${uuid}`, gameSessionDto);
    }

}
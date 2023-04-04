import {getResource, postData} from "../util/axiosApi";
import {RestResponse} from "../api/RestResponse";
import {Player} from "../dto/Player";

/**
 * Player service
 */
export default class PlayerService {

    static URI_V1 = "/api/v1/players";

    register(username: string): Promise<RestResponse<Player>> {
        return postData(`${PlayerService.URI_V1}/${username}`);
    }

    get(): Promise<RestResponse<Player[]>> {
        return getResource(PlayerService.URI_V1)
    }

}
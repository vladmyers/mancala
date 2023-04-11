import {getResource, postData} from "../util/axiosApi";
import {RestResponse} from "../api/RestResponse";
import {PlayerDto} from "../dto/PlayerDto";

/**
 * Player service
 */
export default class PlayerService {

    static URI_V1 = "/api/v1/players";

    register(username: string): Promise<RestResponse<PlayerDto>> {
        return postData(`${PlayerService.URI_V1}/${username}`);
    }

    getBy(uuid: string): Promise<RestResponse<PlayerDto>> {
        return getResource(`${PlayerService.URI_V1}/${uuid}`)
    }

    getAll(): Promise<RestResponse<PlayerDto[]>> {
        return getResource(PlayerService.URI_V1)
    }

}
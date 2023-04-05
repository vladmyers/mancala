import {deleteData, getResource, postData} from "../util/axiosApi";
import {RestResponse} from "../api/RestResponse";
import {WaitingRoomDto} from "../dto/WaitingRoomDto";

/**
 * Waiting Room service
 */
export default class WaitingRoomService {

    static URI_V1 = "/api/v1/waiting-rooms";

    join(playerUuid: string): Promise<RestResponse<WaitingRoomDto>> {
        return postData(`${WaitingRoomService.URI_V1}/${playerUuid}`);
    }

    get(uuid: string): Promise<RestResponse<WaitingRoomDto>> {
        return getResource(`${WaitingRoomService.URI_V1}/${uuid}`)
    }

    leave(uuid: string): Promise<RestResponse<void>> {
        //TODO: use constant WaitingRoomState.LEFT_BY_PLAYER
        let state = "LEFT_BY_PLAYER";
        return deleteData(`${WaitingRoomService.URI_V1}/${uuid}/${state}`);
    }

}
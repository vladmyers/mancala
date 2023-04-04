import {RestResponseError} from "./RestResponseError";

export interface RestResponse<T> {
    uuid: string
    timestamp: Date;
    error?: RestResponseError;
    result?: T
}
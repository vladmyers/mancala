import axios, {AxiosRequestConfig, AxiosResponse} from 'axios';
import {_API_BASE_URL} from "../service/config/Configuration";

const axiosApi = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const getResource = async <T>(url: string, config?: AxiosRequestConfig, notification: boolean = true) => {
    return handleResponse(url, axios.get(_API_BASE_URL + url, {...config}), notification);
}

export const postData = async (url: string, data: object = {}, config?: AxiosRequestConfig, notification: boolean = true) => {
    return handleResponse(url, axios.post(_API_BASE_URL + url, data, {...config}), notification)

};

export const putData = async (url: string, data: object = {}, notification: boolean = true) => {
    return handleResponse(url, axios.put(_API_BASE_URL + url, data), notification);
};

export const deleteData = async (url: string, data: object = {}, notification: boolean = true) => {
    return handleResponse(url, axios.delete(_API_BASE_URL + url, data), notification);
};

const handleResponse = (url: string, axiosResponse: Promise<AxiosResponse>, notification: boolean = true) => {
    //TODO: add error handler and notifications for user
    return axiosResponse.then(response => response.data);
}

export default axiosApi;

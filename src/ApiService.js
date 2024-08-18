import {request} from "./AxiosHelper"

export const sendPostRequest = async (user,endpoint) => {
    try {
        const res = await request('post', '/'+endpoint, user);
        return res.data;
    } catch (error) {
        console.error('Error making POST request', error);
        throw error; // Re-throw the error to be handled by the caller
    }
};
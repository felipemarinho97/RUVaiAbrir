import { Component } from 'react';
import axios from 'axios';
import { API_BASE_URL } from '../common/constants';
import Auth from './auth/Auth';

class API extends Component {

    static signedGetCall(endpoint, options) {
        const token = Auth.getUserAuthToken();
        if (!token)
            return;
        let requestOptions = Object.assign({
            headers: {
                Authorization: `Bearer ${token}`  
            }
        }, options);

        return axios.get(API_BASE_URL + endpoint, requestOptions);
    }

    static getCurrentStatus() {
        return this.signedGetCall('/status');
    }
}

export default API;
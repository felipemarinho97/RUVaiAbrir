import { Component } from 'react';
import axios from 'axios';
import { API_BASE_URL, AUTH_TOKEN } from '../../common/constants';

class Auth extends Component {

    static login(usernameOrEmail, password) {
        return axios.post(API_BASE_URL + '/auth/login', { usernameOrEmail, password }, { 'Content-Type': 'application/json' });
    }

    static register(firstName, lastName, email, username, password) {
        return axios.post(API_BASE_URL + '/auth/register', { firstName, lastName, email, username, password });
    }

    static getUserAuthToken() {
        return localStorage.getItem(AUTH_TOKEN);
    }
    
}

export default Auth;
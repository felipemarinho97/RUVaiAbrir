import { Component } from 'react';
import axios from 'axios';
import { API_BASE_URL } from '../common/constants';
import Auth from './auth/Auth';

class API extends Component {
    static signedCall(method, endpoint, body, options = {}) {
        const token = Auth.getUserAuthToken();
        if (!token)
            return new Promise((_, rej) =>
                rej('O usuário deve estar logado para ter acesso ao sistema!')
            );
        let requestOptions = Object.assign(
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            },
            options
        );

        if (body)
            return axios[method](API_BASE_URL + endpoint, body, requestOptions);
        else return axios[method](API_BASE_URL + endpoint, requestOptions);
    }

    static signedDeleteCall(endpoint, options = {}) {
        const token = Auth.getUserAuthToken();
        if (!token)
            return new Promise((_, rej) =>
                rej('O usuário deve estar logado para ter acesso ao sistema!')
            );
        let requestOptions = Object.assign(
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            },
            options
        );

        return axios.delete(API_BASE_URL + endpoint, requestOptions);
    }

    static signedPutCall(endpoint, comment, options = {}) {
        const token = Auth.getUserAuthToken();
        if (!token)
            return new Promise((_, rej) =>
                rej('O usuário deve estar logado para ter acesso ao sistema!')
            );
        let requestOptions = Object.assign(
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            },
            options
        );

        return axios.put(API_BASE_URL + endpoint, comment, requestOptions);
    }

    static getCurrentStatus() {
        return this.signedCall('get', '/status');
    }

    static getCurrentUserStatus() {
        return this.signedCall('get', '/usuario-status');
    }

    static getComments(pagina, limite, inc) {
        return this.signedCall('get', '/comentarios?tipoDeRefeicao=DINNER');
    }

    static updateComment(id, comment) {
        return this.signedCall('put', '/comentario/' + id, comment);
    }

    static removeComment(id) {
        return this.signedCall('delete', '/comentario/' + id);
    }

    static addComment(comment) {
        return this.signedCall('post', '/comentario', comment);
    }

    static addUserStatus(userStatus) {
        return this.signedCall('post', '/usuario-status', userStatus);
    }

    static addRating(rate) {
        return this.signedCall('post', '/classificacao', rate);
    }

    static getAverageRating() {
        return this.signedCall('get', '/classificacao');
    }
}

export default API;

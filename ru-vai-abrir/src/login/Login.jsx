import React, { Component } from 'react';
import { Form, Input, Icon, Button, notification } from 'antd';
import { Redirect } from 'react-router-dom';

import Auth from '../core/auth/Auth';
import './Login.css';
import { AUTH_TOKEN } from '../common/constants';

const { Item } = Form;

const openNotification = (type, message, description) => {
    notification[type]({
        message, description
    });
};

class Login extends Component {

    constructor(props) {
        super(props);
        console.log(props);
        
        this.state = {
            emailOrUsername: "",
            password: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
    }

    handleLogin(event) {
        event.preventDefault();
        Auth.login(this.state.emailOrUsername, this.state.password)
            .then((res) => {
                localStorage.setItem(AUTH_TOKEN, res.data.accessToken);
                try {
                    this.props.checkLogin();
                } catch (e) {
                    console.log(e);
                    
                }

                openNotification("success","Você está logado!");
            })
            .catch((error) => {
                if (error.response)
                    openNotification("warning","Algo inesperado ocorreu", error.response.data.message)
            })


                   
    }

    handleChange(event) {
        const target = event.target;
        const name = target.name;

        this.setState({ [name]: event.target.value });
    }

    render() {
        return (
            this.props.loggedIn ? 
            (<Redirect to="/status"/>) : (
            <div className="login-container">
                <h1>Entrar</h1>
                <Form onSubmit={this.handleLogin}>
                    <Item>
                        <Input 
                            size="large" name="emailOrUsername" value={this.state.emailOrUsername}
                            onChange={this.handleChange}
                            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            placeholder="Usuário ou Email" />
                    </Item>
                    <Item>
                        <Input 
                            size="large" name="password" value={this.state.password}
                            onChange={this.handleChange}
                            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            type="password" placeholder="Senha" />
                    </Item>
                    <Item>
                        <Button htmlType="submit"><Icon type="login" />Entrar</Button>
                    </Item>
                </Form>
            </div>)
        );
    }

}

export default Login;
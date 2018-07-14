import React, { Component } from 'react';

import { Form, Input, Icon, Button } from 'antd';
import { Redirect } from 'react-router-dom';

import Auth from '../core/auth/Auth';

import './Register.css';

const { Item } = Form;


class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            firstName: '', 
            lastName: '', 
            email: '', 
            username: '', 
            password: '',
            validation: {
                email: {
                    status: '',
                    help: ''
                },
                username: {
                    status: '',
                    help: 'Deve possuir de 8 à 20 caracteres alfanuméricos, podendo conter "_" ou "."'
                },
                password: {
                    status: '',
                    help: ''
                }
            } 
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleRegister = this.handleRegister.bind(this);
        this.validateEmail = this.validateEmail.bind(this);
        this.validateUsername = this.validateUsername.bind(this);
    }

    validatePassword (toValidate) {
        let valid = toValidate.match(/......../);
        if (valid) {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                password: {
                    status: 'success',
                    help: ''
                }})});
        } else {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                password: {
                    status: 'error',
                    help: "A senha deve conter mais de 8 caracteres."
                }})});
        }

    }

    validateUsername (toValidate) {
        let valid = toValidate.match(/^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/);
        this.setState({ validation: Object.assign({}, this.state.validation, {
                username: {
                    status: 'validating',
                    ...this.state.validation.username.help
                }})});
        if (valid) {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                username: {
                    status: 'success',
                    help: ''
                }})});
        } else {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                username: {
                    status: 'error',
                    help: "Por favor insira um nome de usuário válido."
                }})});
        }

    }

    validateEmail (toValidate) {
        let valid = toValidate.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/);
        if (valid) {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                email: {
                    status: 'success',
                    help: ''
                }})});
        } else {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                email: {
                    status: 'error',
                    help: "Por favor insira um endereço de email válido."
                }})});
        }
    }

    handleRegister(event) {
        event.preventDefaults();

        Auth.register(this.state.firstName, 
            this.state.lastName, this.state.email, this.state.username, this.state.password)
            .then(res => {
                
            })
    }

    handleChange(event) {
        const target = event.target;
        const name = target.name;

        switch (name) {
            case "email":
                this.validateEmail(target.value)
                console.log(this.state);

                break;
            case "username":
                this.validateUsername(target.value);
                break;
            case "password":
                this.validatePassword(target.value);
                break;
        }

        this.setState({ [name]: event.target.value });
    }

    render() {        

        return (
            this.props.loggedIn ? (
                <Redirect to="/status" />
            )
            : (<div className="register-container">
                <h1>Registrar-se</h1>
                <Form onSubmit={this.handleRegister}>
                    <Item label="Primeiro nome">
                        <Input 
                            size="large" name="firstName"
                            onChange={this.handleChange}
                            prefix={<Icon type="idcard" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            placeholder="ex: Felipe" />
                    </Item>
                    <Item label="Sobrenome">
                        <Input
                            onChange={this.handleChange}
                            size="large" name="lastName"
                            prefix={<Icon type="skin" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            placeholder="ex: Vasconcelos Marinho" />
                    </Item>
                    <Item validateStatus={this.state.validation.email.status} hasFeedback 
                        help={this.state.validation.email.help} label="E-mail">
                        <Input 
                            size="large" name="email"
                            onChange={this.handleChange}
                            prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            placeholder="Seu endereço de email" />
                    </Item>
                    <Item validateStatus={this.state.validation.username.status} hasFeedback
                        help={this.state.validation.username.help} label="Usuário">
                        <Input 
                            onChange={this.handleChange}
                            size="large" name="username"
                            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            placeholder="Seu nome de usuário" />
                    </Item>
                    <Item validateStatus={this.state.validation.password.status} hasFeedback
                        help={this.state.validation.password.help} label="Senha">
                        <Input 
                            size="large" name="password"
                            onChange={this.handleChange}
                            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} 
                            type="password" placeholder="Mais de 8 caracteres" />
                    </Item>
                    <Item validateStatus={this.state.validation.password.status} hasFeedback
                        help={this.state.validation.password.help} label="Confirmação da Senha">
                        <Input
                            size="large" name="password"
                            onChange={this.handleChange}
                            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                            type="password" placeholder="Re-escreva sua senha" />
                    </Item>
                    <Item>
                        <Button htmlType="submit" size="large">Registrar-se</Button>
                    </Item>
                </Form>
            </div>)
        )
    }

}

export default Register;

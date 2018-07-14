import React, { Component } from 'react';

import { Form, Input, Icon, Button, notification } from 'antd';
import { Redirect } from 'react-router-dom';

import Auth from '../core/auth/Auth';

import './Register.css';

const { Item } = Form;

const openNotification = (type, message, description) => {
    notification[type]({
        message, description
    });
};

class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            registeredSuccessful: false,
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
                },
                password2: {
                    status: '',
                    help: ''
                },
                form: {
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

    validatePassword2 (toValidate) {
        let valid = toValidate === this.state.password;
        if (valid) {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                password2: {
                    status: 'success',
                    help: ''
                }})});
        } else {
            this.setState({ validation: Object.assign({}, this.state.validation, {
                password2: {
                    status: 'error',
                    help: "As senhas devem coincidir."
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

    validateForm() {
        let types = ['email', 'username', 'password', 'password2'];
        let valid = true;

        types.forEach((type) => {
            let status = this.state.validation[type].status;
            if (status !== "" && status !== "success") {
                this.setState({ validation: Object.assign({}, this.state.validation, {
                    form: {
                        status: 'error',
                        help: "Por favor corrija os problemas encontrados."
                    }})});
                valid = false;
            }
        })

        return valid;
    }

    handleRegister(event) {
        event.preventDefault();

        if (!this.validateForm()) {           
            return;
        }       

        Auth.register(this.state.firstName, 
            this.state.lastName, this.state.email, this.state.username, this.state.password)
            .then(res => {
                openNotification("success", "Você está registrado!", "Agora é só fazer login e começar a usar o serviço.");
                this.setState({ registeredSuccessful: true })
            })
            .catch(err => {
                console.log(err.response);
                let title = "Ocorreu um erro inesperado";
                let type = "error";

                if (err.response.status === 400) {
                    title = "Um ou mais campos estão incorretos.";
                    type = "warning"
                }
                
                openNotification(type, title, err.response.data.message);
            });
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
            case "password-2":
                return this.validatePassword2(target.value);
        }

        this.setState({ [name]: event.target.value });
    }

    render() {
        if (this.state.registeredSuccessful) {
            return (<Redirect to="/signin" />)
        }   

        return (
            this.props.loggedIn ? (
                <Redirect to="/" />
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
                    <Item validateStatus={this.state.validation.password2.status} hasFeedback
                        help={this.state.validation.password2.help} label="Confirmação da Senha">
                        <Input
                            size="large" name="password-2"
                            onChange={this.handleChange}
                            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                            type="password" placeholder="Re-escreva sua senha" />
                    </Item>
                    <Item validateStatus={this.state.validation.form.status} 
                        help={this.state.validation.form.help}>
                        <Button htmlType="submit" size="large">Registrar-se</Button>
                    </Item>
                </Form>
            </div>)
        )
    }

}

export default Register;

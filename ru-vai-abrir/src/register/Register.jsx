import React, { Component } from 'react';
import { Form, Input, Icon, Button } from 'antd';
import './Register.css';

const { Item } = Form;

class Register extends Component {

    render() {
        return (
            <div className="register-container">
                <h1>Registrar-se</h1>
                <Form>
                    <Item label="Primeiro nome">
                        <Input size="large" prefix={<Icon type="idcard" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="ex: Felipe" />
                    </Item>
                    <Item label="Sobrenome">
                        <Input size="large" prefix={<Icon type="skin" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="ex: Vasconcelos Marinho" />
                    </Item>
                    <Item label="E-mail">
                        <Input size="large" prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Seu endereço de email" />
                    </Item>
                    <Item label="Usuário">
                        <Input size="large" prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Seu nome de usuário" />
                    </Item>
                    <Item label="Senha">
                        <Input size="large" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Mais de 8 caracteres" />
                    </Item>
                    <Item>
                        <Button size="large">Registrar-se</Button>
                    </Item>
                </Form>
            </div>
        )
    }

}

export default Register;

import React, { Component } from 'react';

import { Layout, Icon, Menu } from 'antd';
import { Link, Route } from 'react-router-dom';

import logo from './food-icon-1.svg';
import './App.css';
import Login from '../login/Login';
import Register from '../register/Register';
import { APP_NAME } from '../common/constants';


const { Header, Content, Footer } = Layout;
const { Item } = Menu;

class App extends Component {
  
  render() {
    return (
      <Layout className="App-container">
        <Header className="App-header">
          <div className="App-logo">
            <img src={logo} alt="logo" /> <a className="App-logo-name">{APP_NAME}</a>
          </div>
          <Menu mode="horizontal" style={{ lineHeight: '63px', float:'right', borderBottom:'none' }}>
            <Item key="1">
              <Link to="/signin">Entrar <Icon type="login" /></Link>
            </Item>
            <Item key="2">
              <Link to="/signup">Registrar</Link>
            </Item>
          </Menu>
        </Header>
        <Content>
          <div className="container">
            <Route path="/signin" component={Login} />
            <Route path="/signup" component={Register} />
          </div>
        </Content>
        <Footer className="App-footer">
          <Icon type="ant-design" style={{float: 'right', fontSize: 20}}/>
          <div className="App-footer-copyright">
            {APP_NAME} <Icon type="copyright" /> Desenvolvido com <Icon type="heart" style={{ fontSize:10 }} /> por Felipe Marinho
          </div>
        </Footer>
      </Layout>
    );
  }
}

export default App;

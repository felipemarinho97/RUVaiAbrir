import React, { Component } from 'react';
import logo from './food-icon-1.svg';
import './App.css';
import { Layout, Button, Icon, Menu } from 'antd';

const { Header, Content, Footer } = Layout;
const { Item } = Menu;

const APP_NAME = "RU Vai Abrir?"

class App extends Component {
  
  render() {
    return (
      <Layout className="App-container">
        <Header className="App-header">
          <div className="App-logo">
            <img src={logo} alt="logo" /> <span className="App-logo-name">{APP_NAME}</span>
          </div>
          <Menu mode="horizontal" style={{ lineHeight: '63px', float:'right', borderBottom:'none' }}>
            <Item key="1">Login</Item>
          </Menu>
        </Header>
        <Content>
          
        </Content>
        <Footer className="App-footer">
          <div className="App-footer-copyright">
            {APP_NAME} <Icon type="copyright" /> Desenvolvido com <Icon type="heart" style={{ fontSize:10 }} /> por Felipe Marinho
          </div>
        </Footer>
      </Layout>
    );
  }
}

export default App;

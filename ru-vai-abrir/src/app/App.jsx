import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Layout, Button, Icon } from 'antd';

const { Header, Content, Footer } = Layout;

class App extends Component {
  
  render() {
    return (
      <Layout className="App-container">
        <Header className="App-header">
          <div className="App-logo">
            <Icon type="clock-circle-o" style={{ fontSize:18 }}/> Ru Vai Abrir?
          </div>
        </Header>
        <Content>
          Lorem Ipsum
        </Content>
        <Footer className="App-footer">
          <div className="App-footer-copyright">RuVaiAbrir? <Icon type="copyright" /> Criado por Felipe Marinho</div>
        </Footer>
      </Layout>
    );
  }
}

export default App;

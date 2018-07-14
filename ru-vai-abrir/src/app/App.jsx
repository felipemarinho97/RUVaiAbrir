import React, { Component } from 'react';

import { Layout, Icon, Menu, Button } from 'antd';
import { Link, Route, Switch, Redirect } from 'react-router-dom';

import logo from './food-icon-1.svg';
import './App.css';
import Login from '../login/Login';
import Register from '../register/Register';
import { APP_NAME, AUTH_TOKEN } from '../common/constants';


const { Header, Content, Footer } = Layout;
const { Item } = Menu;

const FadingRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={props => (
    <Component {...props} />
  )} />
)

class App extends Component {
  constructor(props) {
    super(props)

    this.state = {
      loggedIn: false
    }

    this.handleLogout = this.handleLogout.bind(this);

  }

  componentWillMount() {
    this.checkLogin()
  }

  checkLogin() {
    let token = localStorage.getItem(AUTH_TOKEN);
    if (token && (token.match(/^.+\..+\..+$/))) {
      this.setState({ loggedIn: true })
    } else {
      this.setState({ loggedIn: false })
    }
  }

  handleLogout(event) {
    event.preventDefault();
    
    this.setState({ loggedIn: false });
    localStorage.removeItem(AUTH_TOKEN);

    console.log("espedasd");
    
  }
  
  render() {
    return (
      <Layout className="App-container">
        <Header className="App-header">
          <div className="App-logo">
            <img src={logo} alt="logo" /> <a className="App-logo-name">{APP_NAME}</a>
          </div>
          { this.state.loggedIn ?
          (<Menu mode="horizontal" style={{ lineHeight: '63px', float:'right', borderBottom:'none' }}>
              <Item key="3">
                <Link to="/" onClick={this.handleLogout}>Sair <Icon type="logout" /></Link>
              </Item>
          </Menu>) :
          (<Menu mode="horizontal" style={{ lineHeight: '63px', float: 'right', borderBottom: 'none' }}>

            <Item key="1">
              <Link to="/signin">Entrar</Link>
            </Item>
            <Item key="2">
              <Link to="/signup">Registrar</Link>
            </Item>
          </Menu>)
          }
        </Header>
        <Content>
          <div className="container">
            <Switch>
              {/* {this.state.loggedIn ?
                (<Redirect to="/status" />)
              : (<div>Não logado</div>)} */}
              <Route path="/signin" component={(props) => (<Login checkLogin={() => this.checkLogin()} loggedIn={this.state.loggedIn}/>)} />
              <Route path="/signup" component={(props) => (<Register loggedIn={this.state.loggedIn} />)} />
            </Switch>
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

import React, { Component } from 'react';
import { Tabs, List } from 'antd';

import { Redirect, Link } from 'react-router-dom';
import Media from 'react-media';
import { IceCream, Ghost, SpeechBubble, Planet } from 'react-kawaii';
import API from '../core/API';

import "./Status.css";
const emojis = [IceCream, Ghost, SpeechBubble, Planet];
const Emoji = emojis[Math.floor(Math.random() * emojis.length)];

class Status extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentStatus: 'Carregando...',
            currentStatus: 'Fechado',
            currentUserStatus: 'Carregeando...'
        }
    }

    componentWillMount() {
        this.getCurrentStatus();
        this.getCurrentUserStatus();
    }

    getCurrentStatus() {
        let status;
        API.getCurrentStatus().then(res => {
            console.log(res);
            this.setState({ currentStatus: res.data.status })
            
        }).catch(err => {
            console.log(err.response);
            // if (err.response)
            //     this.setState({ currentStatus: err.response.data.message })
            

            
        })
        return status;
    }

    getCurrentUserStatus() {
        let status;
        API.getCurrentUserStatus().then(res => {
            let userStatus = res.data.restaurantStatus;
            if (userStatus === 'OPENED')
                userStatus = "Aberto";
            else if (res.data.restaurantStatus === 'CLOSED') {
                userStatus = "Fechado"
            }
            console.log(res);
            this.setState({ currentUserStatus: userStatus })

        }).catch(err => {
            console.log(err.response);
            if (err.response)
                this.setState({ currentUserStatus: err.response.data.message })
        })
        return status;
    }

    render() {

        const StatusTabs = ({ position = "right" }) => (
            <Tabs tabPosition={position} defaultActiveKey="1">
                <Tabs.TabPane tab="Oficial" key="1" style={{ textAlign: 'center' }}>
                    <div className="status-wrapper">
                        <Emoji size={150} mood="sad" />
                    </div>
                    {(this.state.currentStatus === "Aberto") ?
                        (<div><h2><strong>Aberto!</strong></h2>Que tal consultar o <Link to="/cardapio">cardápio do dia</Link> também pra ver o que tá rolando de bom?</div>)
                      : (this.state.currentStatus === "Fechado") ? 
                            (<div><h2><strong>Fechado.</strong></h2>Poxa, que pena.. tente voltar mais tarde e verificar novamente.</div>)
                          : (<div>{this.state.currentStatus}</div>)}
                </Tabs.TabPane>
                <Tabs.TabPane tab="Usuários" key="2" style={{ textAlign: 'center' }}>
                    <div className="status-wrapper">
                        <Emoji size={150} mood="sad" />
                    </div>{this.state.currentUserStatus}
                </Tabs.TabPane>
            </Tabs>
        );

        if (!this.props.loggedIn) {
            return (<Redirect to="/"/>)
        }

        return (
            <div className="status-container">
                <h1>Status</h1>
                <Media query={{ minWidth: 400 }} >{
                    matches => 
                        matches ? (<StatusTabs />) : (<StatusTabs position="top" />)
                }</Media>
                <h3 style={{paddingTop: '2rem'}}>Comentários</h3>
                <List loading={true}/>
            </div>
        )
    }
}

export default Status;
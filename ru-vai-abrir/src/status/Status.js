import React, { Component } from 'react';
import { Tabs } from 'antd';

import { IceCream, Ghost, Mug, Planet } from 'react-kawaii';
import API from '../core/API';

import "./Status.css";
const emojis = [IceCream, Ghost, Mug, Planet];
let Emoji = emojis[Math.floor(Math.random() * emojis.length)];

class Status extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentStatus: 'Carregando...',
            currentUserStatus: 'Carregeando...'
        }
    }

    componentWillMount() {
        this.getCurrentStatus();
    }

    getCurrentStatus() {
        let status;
        API.getCurrentStatus().then(res => {
            console.log(res);
            
        }).catch(err => {
            console.log(err.response);
            this.setState({ currentStatus: err.response.data.message })
             

            
        })
        return status;
    }

    render() {

        return (
            <div className="status-container">
                <h1>Status</h1>
                <Tabs tabPosition="left" defaultActiveKey="1">
                    <Tabs.TabPane tab="Oficial" key="1" style={{textAlign: 'center'}}>
                        <div style={{ margin: 'auto' }}><Emoji size={150} mood="sad"/></div>{this.state.currentStatus}
                    </Tabs.TabPane>
                    <Tabs.TabPane tab="Usuários" key="2">{this.state.currentUserStatus}</Tabs.TabPane>
                </Tabs>
            </div>
        )
    }
}

export default Status;
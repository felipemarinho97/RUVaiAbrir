import React, { Component } from 'react';
import { Tabs, Badge, Rate, Tooltip } from 'antd';

import { Redirect, Link } from 'react-router-dom';
import Media from 'react-media';
import { IceCream, Ghost, SpeechBubble, Planet } from 'react-kawaii';
import API from '../core/API';

import './Status.css';
import Comments from '../comments/Comments';
import { openNotification } from '../login/Login';

const emojis = [IceCream, Ghost, SpeechBubble, Planet];
const Emoji = emojis[Math.floor(Math.random() * emojis.length)];

class Status extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentStatus: 'Carregando...',
            currentUserStatus: 'Carregeando...',
            rating: 0,
        };
    }

    componentWillMount() {
        this.getCurrentStatus();
        this.getCurrentUserStatus();
        this.getCurrentRating();
    }

    getCurrentStatus() {
        let status;
        API.getCurrentStatus()
            .then(res => {
                let status = res.data.restaurantStatus;

                if (status === 'OPENED') status = 'Aberto';
                else if (res.data.restaurantStatus === 'CLOSED') {
                    status = 'Fechado';
                }

                this.setState({ currentStatus: status });
            })
            .catch(err => {
                console.log(err.response);
                if (err.response)
                    this.setState({ currentStatus: err.response.data.message });
            });
        return status;
    }

    getCurrentUserStatus() {
        let status;
        API.getCurrentUserStatus()
            .then(res => {
                let userStatus = res.data.restaurantStatus;
                if (userStatus === 'OPENED') userStatus = 'Aberto';
                else if (res.data.restaurantStatus === 'CLOSED') {
                    userStatus = 'Fechado';
                }

                this.setState({ currentUserStatus: userStatus });
            })
            .catch(err => {
                console.log(err.response);
                if (err.response)
                    this.setState({
                        currentUserStatus: err.response.data.message,
                    });
            });
        return status;
    }

    setRating(rate) {
        API.addRating({ rating: rate * 2 }).catch(error => {
            openNotification(
                'warning',
                'Algo inesperado ocorreu',
                error.response.data.message
            );
        });
    }

    getCurrentRating() {
        API.getAverageRating().then(response => {
            this.setState({ rating: response.data.rating });
        });
    }

    render() {
        const StatusTabs = ({ position = 'right' }) => (
            <Tabs tabPosition={position} defaultActiveKey="1">
                <Tabs.TabPane
                    tab="Oficial"
                    key="1"
                    style={{ textAlign: 'center' }}
                >
                    <div className="status-wrapper">
                        <Emoji
                            size={150}
                            mood={
                                this.state.currentStatus === 'Aberto'
                                    ? 'blissful'
                                    : 'sad'
                            }
                        />
                    </div>
                    {this.state.currentStatus === 'Aberto' ? (
                        <div>
                            <h2>
                                <strong>Aberto!</strong>
                            </h2>
                            Que tal consultar o{' '}
                            <Link to="/cardapio">cardápio do dia</Link> também
                            pra ver o que tá rolando de bom?
                        </div>
                    ) : this.state.currentStatus === 'Fechado' ? (
                        <div>
                            <h2 style={{ fontWeight: 'bolder' }}>Fechado.</h2>
                            Poxa, que pena.. tente voltar mais tarde e verificar
                            novamente.
                        </div>
                    ) : (
                        <div>{this.state.currentStatus}</div>
                    )}
                </Tabs.TabPane>
                <Tabs.TabPane
                    tab="Usuários"
                    key="2"
                    style={{ textAlign: 'center' }}
                >
                    <div className="status-wrapper">
                        <Emoji
                            size={150}
                            mood={
                                this.state.currentUserStatus === 'Aberto'
                                    ? 'blissful'
                                    : 'sad'
                            }
                        />
                    </div>
                    {this.state.currentUserStatus === 'Aberto' ? (
                        <div>
                            <h2>
                                <strong>Aberto!</strong>
                            </h2>
                            Que tal consultar o{' '}
                            <Link to="/cardapio">cardápio do dia</Link> também
                            pra ver o que tá rolando de bom?
                        </div>
                    ) : this.state.currentUserStatus === 'Fechado' ? (
                        <div>
                            <h2>
                                <Badge className="title" count={20}>
                                    Fechado.
                                </Badge>
                            </h2>
                            Poxa, que pena.. tente voltar mais tarde e verificar
                            novamente.
                        </div>
                    ) : (
                        <div>{this.state.currentUserStatus}</div>
                    )}
                </Tabs.TabPane>
            </Tabs>
        );

        const Rating = () => (
            <div style={{ margin: '0 auto' }}>
                <Tooltip
                    placement="bottomLeft"
                    title={'Classifique a refeição do dia.'}
                >
                    <span style={{ display: 'none' }}>.</span>
                    <Rate
                        value={this.state.rating}
                        onChange={this.setRating}
                        allowHalf
                        style={{
                            margin: '0 auto',
                            padding: '1rem',
                            fontSize: '2rem',
                        }}
                    />
                </Tooltip>
            </div>
        );

        if (!this.props.loggedIn) {
            return <Redirect to="/" />;
        }

        return (
            <div className="status-container">
                <h1>Status</h1>
                <Media query={{ minWidth: 400 }}>
                    {matches =>
                        matches ? <StatusTabs /> : <StatusTabs position="top" />
                    }
                </Media>
                <div className="Rating-container">
                    <Rating />
                </div>
                <h3 style={{ paddingTop: '2rem' }}>Comentários</h3>
                <Comments />
            </div>
        );
    }
}

export default Status;

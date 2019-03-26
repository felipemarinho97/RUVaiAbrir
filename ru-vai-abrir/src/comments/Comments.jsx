import React, { Component } from 'react';
import { List, Layout, Input, Icon, Form } from 'antd';

import API from '../core/API';
import Comment from './Comment';

class Comments extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loadingComments: true,
            comments: [],
            typingComment: false,
            commentBody: '',
        };

        this.removeComment = this.removeComment.bind(this);
    }

    componentWillMount() {
        this.getCurrentComments();
    }

    getCurrentComments() {
        this.setState({ loadingComments: true });

        API.getComments().then(res => {
            console.log(res.data.content);

            this.setState({
                ...this.state,
                comments: res.data.content,
                loadingComments: false,
            });
        });
    }

    addComment(comment) {
        comment.mealType = this.getCurrentMealType();
        comment.date = new Date();
        API.addComment(comment).then(res => {
            console.log(res);
            this.setState({
                comments: [res.data, ...this.state.comments],
            });
        });
    }

    getCurrentMealType() {
        const date = new Date();

        if (date.getHours() > 10 && date.getHours() < 15) return 'LUNCH';
        else return 'DINNER';
    }

    removeComment(id) {
        this.setState({
            comments: this.state.comments.filter(elm => elm.id !== id),
        });
    }

    handleKeyPress(event) {
        const ENTER = 13;
        const ESC = 27;
        switch (event.keyCode) {
            case ENTER:
                this.addComment({ message: event.target.value });
            case ESC:
                this.setState({ typingComment: false });
                break;
        }
    }

    render() {
        console.log('comments list redered');

        return (
            <Layout>
                <Form style={{ maxWidth: '685px', textAlign: 'center' }}>
                    <Form.Item>
                        {!this.state.typingComment ? (
                            <a
                                onClick={() =>
                                    this.setState({ typingComment: true })
                                }
                                style={{
                                    cursor: 'pointer',
                                    maxWidth: '30ch',
                                    margin: '1rem auto',
                                }}
                            >
                                <Icon style={{ fontSize: 20 }} type="form" />{' '}
                                Escreva um comentário...
                            </a>
                        ) : (
                            <Input.TextArea
                                placeholder="Escreva um comentário..."
                                maxLength={255}
                                autosize={{ minRows: 2, maxRows: 6 }}
                                onKeyDown={this.handleKeyPress.bind(this)}
                            />
                        )}
                    </Form.Item>
                </Form>
                <List
                    loading={this.state.loadingComments}
                    itemLayout="vertical"
                    dataSource={this.state.comments}
                    renderItem={item => (
                        <Comment
                            key={item.id}
                            item={item}
                            removeFromComments={this.removeComment}
                        />
                    )}
                />
            </Layout>
        );
    }
}

export default Comments;

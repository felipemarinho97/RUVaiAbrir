import React, { Component } from 'react';
import { Avatar, Icon, Popconfirm, Tooltip, Rate, Input, List } from 'antd';
import { Link } from 'react-router-dom';
import API from '../core/API';

const { TextArea } = Input;

const RemoveAction = ({ confirmAction }) => (
    <Tooltip placement="bottom" title={"Remover"}>
        <Popconfirm title="Remover comentário?" placement="right"
            onConfirm={confirmAction}
            okText="Sim" cancelText="Não">
            <Icon type="delete" />
        </ Popconfirm>
    </Tooltip>
);

const EditAction = ({ setEditing }) => (
    <Tooltip placement="bottom" title={"Editar"}>
        <Icon
            onClick={setEditing}
            type="edit" />
    </Tooltip>
)

class Comment extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isEditing: false,
            message: this.props.item.message
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        this.setState({ message: e.target.value })
    }

    updateComment(id) {
        const comment = {
            message: this.state.message
        }
        API.updateComment(id, comment).then(res => {
            this.setState({ isEditing: false, message: res.data.message});
            console.log(res);
            
        })
        
    }

    removeComment(id) {
        API.removeComment(id).then(() => {
            this.props.removeFromComments(id);
        }).catch(err => {
            console.log(err.response);

        })

    }

    render() {
        const item = this.props.item;

        return (
            <List.Item 
                actions={[<EditAction setEditing={() => this.setState({ isEditing: true })} />, <RemoveAction confirmAction={() => this.removeComment(item.id)} />]}
                extra={<Rate disabled defaultValue={item.rating} />} >
                <List.Item.Meta
                    avatar={<Avatar>{item.user.firstName[0]}</Avatar>}
                    title={[<Avatar style={{ marginRight: '1rem' }}>{item.user.firstName[0]}</Avatar>, <Link to={`/user/${item.user.username}`}>{item.user.firstName}</Link>]}
                    description={item.user.email} />
                {
                    !this.state.isEditing ?
                        (<div>{this.state.message}</div>)
                    : (<TextArea
                            value={this.state.message}
                            onChange={this.handleChange}
                            onPressEnter={() => this.updateComment(item.id)}
                            maxLength={255} autosize={{ minRows: 2, maxRows: 6 }} />)
                }


            </List.Item>
        )
    }
}

export default Comment;
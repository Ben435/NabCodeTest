import React from "react";
import './item-details.scss';
import {withRouter} from "react-router-dom";

class ItemDetails extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentItem: this.props.item ? {...this.props.item} : {}
        };

        this.save = this.save.bind(this);
        this.delete = this.delete.bind(this);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.item !== this.props.item) {
            this.setState({currentItem: {...this.props.item}})
        }
    }

    updateField(fieldName) {
        return (e) => {
            const val = e.target.value;
            const newItem = {
                ...this.state.currentItem,
                [fieldName]: val
            };
            this.setState({
                currentItem: newItem
            });
        }
    }

    save() {
        fetch(`/api/grocery`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                ...this.state.currentItem
            })
        })
            .then(r => r.json())
            .then(r => this.setState({currentItem: r}));
    }

    delete() {
        fetch(`/api/grocery/${this.state.currentItem.id}`, {
            method: 'DELETE'
        }).then(this.props.history.push('/'));
    }

    render() {
        const {name, category} = this.state.currentItem;

        return (
            <div className="details">
                <div className="actions">
                    <button onClick={this.save}>Save</button>
                    <button onClick={this.delete}>Delete</button>
                </div>
                <div className="fields">
                    <div>
                        <label htmlFor='name'>Name:</label><input id='name' onChange={this.updateField('name').bind(this)}
                                                                  value={name}/>
                    </div>
                    <div>
                        <label htmlFor='category'>Category:</label><input id='category'
                                                                          onChange={this.updateField('category').bind(this)}
                                                                          value={category}/>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(ItemDetails);
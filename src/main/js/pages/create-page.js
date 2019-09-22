import React from 'react';
import EditableItemDetails from "../components/editable-item-details";

export default class CreatePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            item: {}
        };

        this.save = this.save.bind(this);
        this.onChangeItem = this.onChangeItem.bind(this);
    }

    save() {
        fetch(`/api/grocery`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.item)
        })
            .then(() => this.props.history.push(''));
    }
    onChangeItem(newItem) {
        this.setState({item: newItem});
    }

    render() {
        const {item} = this.state;

        return (
            <div className="details">
                <div className="actions">
                    <button onClick={this.save}>Save</button>
                </div>
                <div className="item-details">
                    <EditableItemDetails item={item} onChange={this.onChangeItem}/>
                </div>
            </div>
        )
    }
}
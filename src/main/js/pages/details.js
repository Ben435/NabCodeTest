import React from 'react';
import {withRouter} from "react-router-dom";
import EditableItemDetails from "../components/editable-item-details";
import './details.scss';

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            item: null,
            loading: false
        };

        this.save = this.save.bind(this);
        this.delete = this.delete.bind(this);
        this.onChangeItem = this.onChangeItem.bind(this);
    }

    componentDidMount() {
        if (this.props.match.params.id) {
            this.loadItem(this.props.match.params.id)
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.id !== prevProps.match.params.id) {
            this.loadItem(this.props.match.params.id)
        }
    }

    loadItem(id) {
        this.setState({loading: true});
        fetch(`/api/grocery/${id}`)
            .then(r => r.json())
            .then(r => this.setState({item: r, loading: false}))
            .catch(e => {
                console.error("Error retrieving item: ", e);
                this.setState({item: null, loading: false})
            })
    }

    save() {
        fetch(`/api/grocery`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.item)
        })
            .then(() => this.props.history.push(''));
    }

    delete() {
        fetch(`/api/grocery/${this.state.item.id}`, { method: 'DELETE' })
            .then(() => this.props.history.push(''));
    }

    onChangeItem(newItem) {
        this.setState({item: newItem});
    }

    render() {
        const {loading, item} = this.state;
        return (
            <div className="details">
                <div className="actions">
                    <button disabled={loading} onClick={this.save}>Save</button>
                    <button disabled={loading} onClick={this.delete}>Delete</button>
                </div>
                <div className="item-details">
                    { loading ? <p>Loading...</p> : null}
                    { !loading && !item ? <p>None found for id!</p> : null}
                    { !loading && item ? <EditableItemDetails item={item} onChange={this.onChangeItem}/> : null}
                </div>
            </div>
        )
    }
}

export default withRouter(Home);
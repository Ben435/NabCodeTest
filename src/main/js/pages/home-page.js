import React from 'react';
import './home-page.scss'
import {withRouter} from "react-router-dom";
import Search from '../components/search'

class HomePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            items: null,
            loading: true
        };

        this.bootstrap = this.bootstrap.bind(this);
        this.onUpdateItems = this.onUpdateItems.bind(this);
        this.createNew = this.createNew.bind(this);
    }

    bootstrap() {
        fetch('/api/grocery/bootstrap', {method: 'PUT'});
    }

    createNew() {
        this.props.history.push('/create');
    }

    navigateToItemCallback(item) {
        return () => this.props.history.push('/details/' + item.id);
    }

    onUpdateItems(newState) {
        this.setState({loading: newState.loading, items: newState.items});
    }

    render() {
        const {items, loading} = this.state;

        return (
            <div className="home">
                <div className="actions">
                    <button onClick={this.bootstrap}>Bootstrap</button>
                    <button onClick={this.createNew}>Create New Grocery</button>
                </div>
                <Search itemsCallback={this.onUpdateItems} />
                <hr/>
                <div className="itemsContainer">
                    { loading && <p>Loading...</p>  }
                    { !loading && items.map(i => <div key={i.id} className="grid-item" onClick={this.navigateToItemCallback(i).bind(this)}>{i.name}</div>)}
                </div>
            </div>
        )
    }
}

export default withRouter(HomePage);
import React from 'react';
import {withRouter} from "react-router-dom";
import ItemDetails from "../components/item-details";

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            item: null,
            loading: false
        }
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
            .catch(e => this.setState({item: null, loading: false}))
    }

    render() {
        const {loading, item} = this.state;
        return (
            <div>
                { loading ? <p>Loading...</p> : null}
                { !loading && !item ? <p>None found for id!</p> : null}
                { !loading && item ? <ItemDetails item={item}/> : null}
            </div>
        )
    }
}

export default withRouter(Home);
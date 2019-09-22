import React from 'react';
import './home.scss'
import {withRouter} from "react-router-dom";

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            items: null,
            loading: true
        };

        this.bootstrap = this.bootstrap.bind(this);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.location !== prevProps.location) {
            const searchParams = new URLSearchParams(this.props.location.search);
            if (searchParams.get('refresh')) {
                this.refresh();
            }
        }

    }

    componentDidMount() {
        if (this.state.items === null) {
            this.refresh();
        }
    }

    refresh() {
        fetch('/api/grocery')
            .then(r => r.json())
            .then(r => {
                console.log(r);
                this.setState({items: r, loading: false})
            })
    }

    bootstrap() {
        fetch('/api/grocery/bootstrap').then(() => console.log("Bootstrapped!"));
    }

    navigateToItemCallback(item) {
        return () => this.props.history.push('/' + item.id);
    }

    render() {
        const {items, loading} = this.state;

        return (
            <div className="home">
                <button onClick={this.bootstrap}>Bootstrap</button>
                {/*<SearchBox />*/}
                <div className="itemsContainer">
                    { loading && <p>Loading...</p>  }
                    { !loading && items.map(i => <div key={i.id} className="grid-item" onClick={this.navigateToItemCallback(i).bind(this)}>{i.name}</div>)}
                </div>
            </div>
        )
    }
}

export default withRouter(Home);
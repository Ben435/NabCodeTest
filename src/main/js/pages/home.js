import React from 'react';
import './home.scss'
import {withRouter} from "react-router-dom";

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            items: [],
            loading: true
        };

        this.bootstrap = this.bootstrap.bind(this);
    }

    componentDidMount() {
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
                {/*<Items />*/}
                <div className="itemsContainer">
                    { loading && <p>Loading...</p>  }
                    { !loading && items.map(i => <div key={i.id} className="grid-item" onClick={this.navigateToItemCallback(i).bind(this)}>{i.name}</div>)}
                </div>
            </div>
        )
    }
}

export default withRouter(Home);
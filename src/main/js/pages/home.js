import React from 'react';
import './home.scss'

export default class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            items: [],
            loading: true
        }
    }

    componentDidMount() {
        fetch('/api/grocery')
            .then(r => r.json())
            .then(r => {
                console.log(r);
                this.setState({items: r, loading: false})
            })
    }

    render() {
        const {items} = this.state;
        return (
            <div className="home">
                {/*<SearchBox />*/}
                {/*<Items />*/}
                <p>Home</p>
                <div className="itemsContainer">
                    { items.map(i => <div key={i.id} className="grid-item">{i.name}</div>)}
                </div>
            </div>
        )
    }
}
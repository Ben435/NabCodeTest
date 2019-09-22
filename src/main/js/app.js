import React, {Component} from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import Home from './pages/home';
import Details from './pages/details';
import './app.scss';

export default class App extends Component {
    constructor(props) {
        super(props);
    }

    bootstrap() {
        fetch('/api/grocery/bootstrap').then(() => console.log("Bootstrapped!"));
    }

    render() {
        const bootstrap = this.bootstrap.bind(this);

        return (
            <Router>
                <div className="app">
                    <h1>Hello world!</h1>
                    <button onClick={bootstrap}>Bootstrap</button>
                    <Route path="/" exact component={Home} />
                    <Route path="/:id" component={Details} />
                </div>
            </Router>
        )
    }
}
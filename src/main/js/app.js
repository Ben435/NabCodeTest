import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import Home from './pages/home';
import Details from './pages/details';
import Create from './pages/create';
import './app.scss';

export default class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router>
                <div className="app">
                    <h1>NAB Code Test</h1>
                    <Route path="/" exact component={Home} />
                    <Route path="/create" component={Create} />
                    <Route path="/details/:id" component={Details} />
                </div>
            </Router>
        )
    }
}
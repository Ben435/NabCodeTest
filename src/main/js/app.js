import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import HomePage from './pages/home-page';
import DetailsPage from './pages/details-page';
import CreatePage from './pages/create-page';
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
                    <Route path="/" exact component={HomePage} />
                    <Route path="/create" component={CreatePage} />
                    <Route path="/details/:id" component={DetailsPage} />
                </div>
            </Router>
        )
    }
}
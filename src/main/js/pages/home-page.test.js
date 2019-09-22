import {cleanup, render} from "@testing-library/react";
import React from "react";
import HomePage from "./home-page";
import {MemoryRouter, Route} from "react-router-dom";

afterEach(cleanup);

test('does render', () => {
    window.fetch = jest.fn(url => {
        let data;
        if (url === '/api/category') {
            data = ['cat1, cat2'];
        } else if (url === '/api/grocery') {
            data = [{
                id: '1234',
                name: 'fruit_1',
                category: 'fruit'
            },{
                id: '4321',
                name: 'fruit_2',
                category: 'fruit'
            }]
        }
        return Promise.resolve({json: () => Promise.resolve(data)})
    });

    render(
        <MemoryRouter initialEntries={[{ pathname: "/"}]}>
            <Route exact path="/" component={HomePage} />
        </MemoryRouter>
    );
});

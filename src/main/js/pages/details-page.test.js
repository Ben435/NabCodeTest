import {render} from "@testing-library/react";
import React from "react";
import Details from "./details-page";
import {MemoryRouter, Route} from "react-router-dom";

test('does render', () => {
    window.fetch = jest.fn(url => {
        if (url === '/api/grocery/1234') {
            return Promise.resolve({
                json: () => Promise.resolve({
                    id: '1234',
                    name: 'fruit_1',
                    category: 'fruit'
                })
            });
        } else {
            throw new Error("bad request for `fetch`")
        }
    });

    const {baseElement} = render(
        <MemoryRouter initialEntries={[{ pathname: "/details/1234"}]}>
            <Route exact path="/details/:id" component={Details} />
        </MemoryRouter>
    );

    const buttons = baseElement.querySelectorAll(".actions > button");

    expect(buttons.length).toBe(2);

    buttons[0].click();

    console.log(buttons)
});
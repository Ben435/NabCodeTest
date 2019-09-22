import React from 'react';
import Search from "./search";
import {unmountComponentAtNode} from "react-dom";
import {render} from "@testing-library/react";
import {waitForElement} from "@testing-library/dom";
import * as ReactTestUtils from "react-dom/test-utils";

let container;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
});
afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});

describe("Search component", () => {
    afterEach(() => global.fetch.mockClear());

    test("Categories load", async () => {
        global.fetch = jest.fn().mockImplementation(url => {
            let data;
            if (url === '/api/category') {
                data = ['cat1', 'cat2'];
                return Promise.resolve({ json: () => Promise.resolve(data)})
            } else {
                throw new Error("Unexpected fetch: " + url);
            }
        });

        const { queryByText } = render(<Search/>, container);

        // Should load categories
        const cat1 = await waitForElement(() => queryByText("Cat1"));
        const cat2 = await waitForElement(() => queryByText("Cat2"));

        expect(cat1).toBeTruthy();
        expect(cat2).toBeTruthy();
    });

    test("Search calls search with correct params", async done => {
        const pattern = [
            {
                url: "/api/category",
                data: []
            },
            {
                url: '/api/grocery',
                data: []
            },
            {
                url: '/api/grocery?name=hello',
                data: [],
                callback: () => done()
            }
        ].reverse();    // Reverse as I want to use `pop` to move through them, but `pop` starts at the back.
        global.fetch = jest.fn().mockImplementation(url => {
            let cur = pattern.pop();
            if (url === cur.url) {
                if (cur.callback) {
                    cur.callback();
                }
                return Promise.resolve({ json: () => Promise.resolve(cur.data)})
            } else {
                expect(url).toEqual(cur.url);
            }
        });

        const { getByText, getByPlaceholderText } = render(<Search itemsCallback={() => {}} />, container);

        const searchBox = getByPlaceholderText("Search by name");
        searchBox.value = "hello";
        ReactTestUtils.Simulate.change(searchBox);

        const searchButton = getByText("Search");

        ReactTestUtils.Simulate.click(searchButton);
    })
});
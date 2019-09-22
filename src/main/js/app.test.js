import React from 'react';
import {cleanup, render} from '@testing-library/react';
import App from "./app";

afterEach(cleanup);

test('does render', () => {
  // Http mock
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

  const {} = render(<App />);
});

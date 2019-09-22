import React from 'react';
import {cleanup, render} from '@testing-library/react';

afterEach(cleanup);

test('does render', () => {
  const {} = render('<App />');
});

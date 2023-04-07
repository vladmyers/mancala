import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter} from 'react-router-dom';

import configure from "./service/config/Configuration";
import App from './App';

configure();

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
        <BrowserRouter>
            <App/>
        </BrowserRouter>
);

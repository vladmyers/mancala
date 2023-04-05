import React from 'react';
import {Route, Routes} from 'react-router-dom';

import Home from "./page/Home";
import About from "./page/About";
import Registration from "./game/Registration";
import WaitingRoom from "./game/WaitingRoom";

import 'bootstrap/dist/css/bootstrap.min.css';
import GameSession from "./game/GameSession";

const App = () => {
    return (
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/game-session" element={<GameSession/>}/>
            <Route path="/register" element={<Registration/>}/>
            <Route path="/waiting-room" element={<WaitingRoom/>}/>
            <Route path="/about" element={<About/>}/>
        </Routes>
    );
};

export default App;

import React from 'react';

import Header from "../component/Header";
import Body from "../component/Body";

const About = () => {
    return (
        <>
            <Header/>
            <Body>
                <h1>About Mancala</h1>
                <p>Mancala is a family of board games played around the world, sometimes called "sowing" games, or "count-and-capture"
                    games, which describes the gameplay. The word mancala comes from the Arabic word naqala meaning literally "to move."</p>
                <p>The objective of the game is to capture more seeds than one's opponent. The game has a long history, with evidence of its
                    existence dating back to ancient Egypt and the African continent. The game is still widely played today and has many
                    variations.</p>
                <h2>How to Play</h2>
                <p>Players take turns sowing their seeds, or stones, around the board. The seeds are distributed one by one in each hole,
                    moving counter-clockwise around the board. If the last seed falls into an empty hole on the player's side, the player
                    captures all of the seeds in the hole directly opposite it and places them in their store. If the last seed falls into
                    the player's store, they get an extra turn. The game ends when one player captures all the seeds or when one player is
                    unable to make a move.</p>
                <p>There are many different variations of Mancala with different rules and strategies. Some variations have multiple rows of
                    holes or different numbers of seeds, and some allow players to sow in both directions around the board. Regardless of
                    the variation, Mancala is a fun and challenging game that has been enjoyed for centuries.</p>
            </Body>
        </>
    );
};

export default About;

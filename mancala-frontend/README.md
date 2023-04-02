# Mancala Game

This is a simple implementation of the Mancala game using React.js, TypeScript, npm, and Bootstrap.

## How to Run

1. Clone the repository.
2. Open the terminal and navigate to the project directory.
3. Run `npm install` to install the dependencies.
4. Run `npm start` to start the development server.
5. Open your browser and navigate to `http://localhost:3000` to play the game.

## How to Play

1. The game starts with four stones in each of the 12 small pits.
2. Players take turns picking up all the stones from one of the small pits on their side of the board and distributing them counterclockwise, one stone at a time, into the other pits on the board.
3. If the last stone ends up in the player's mancala (the large pit on their side of the board), they get another turn.
4. If the last stone ends up in an empty small pit on the player's side of the board, they capture that stone and any stones in the pit opposite it, and place them in their mancala.
5. The game ends when all the small pits on one side of the board are empty. The player with the most stones in their mancala wins.

# Mancala Game

## Table of Contents

* [Intro]
  * [About the game]
  * [Project Scope and Limitations]
    * [Scope]
    * [Objectives]
    * [Limitations]
    * [Project Status]
    * [Ideas]
* [How to run]
  * [Easy Way]
  * [Build Manually]
    * [Build all modules]
    * [Build modules separately]
  * [Usage]
* [OpenAPI Specification]
* [Tech stack]
  * [Common - across all modules]
  * [mancala-frontend module]
  * [mancala-backend module]
  * [Using Checkstyle]
    * [Project configuration]
    * [Plugin for IntelliJ IDEA]
* [License]

# Intro

This is an implementation of the Mancala game with two players.

The game is implemented using a maven multi module project with the following modules:

* **[mancala-frontend]:** the frontend module provides a user-friendly interface to play the game
* **[mancala-backend]:** the backend module handles the game logic and communicate with the frontend module

## About the game

Mancala is an ancient two-player strategy board game played with small stones, beans, or seeds, and rows of holes or pits on a board.
The objective of the game is to capture more seeds than the opponent by collecting them from their holes and depositing them into the player's store.

1. The game usually begins with four stones in each pit. In this implementation every pit has 6 stones
2. Players take turns selecting one of their pits and redistributing its stones counterclockwise, one into each pit including their own 
   mancala (the large pit at the end of their row), but not their opponent's mancala
3. If the last stone ends up in the player's mancala, they get to take another turn
4. If the last stone ends up in an empty pit on their side of the board, they capture that stone and any stones in the opposite pit on 
   their opponent's side of the board and add them to their own mancala
5. The game ends when one player has no more stones on their side of the board
6. The player with the most stones in their mancala at the end of the game wins

## Project Scope and Limitations

### Scope

The scope of this project is to develop a Mancala game.
The game will have a Waiting Room feature, which allows multiple players to join the game and wait for a new game session to start.
Once the game session starts, the players will be paired and the game will begin.

### Objectives
1. To develop a Waiting Room feature that allows players to enter their names and join the waiting room to wait for a match and start 
   the game. The feature should also allow players to leave a certain waiting room.
2. To develop a Game Session feature that allows two connected and matched players to start playing the game. The feature should also 
   allow players to leave a game session, and notify them if their opponent has left the game.  
3. To support parallel game sessions, so that more than one game session can be played simultaneously.
4. To inform players of any errors that occur, and provide them with details such as player's registration result, waiting room ID, 
   created date and time, state, opponent player's name, etc.

### Limitations
The following are the limitations of this project:
1. The game will only be playable on desktop and laptop devices, and it will not be optimized for mobile devices.
2. The game will have basic styling and will not have any advanced graphics or animation.
3. The game will not have an AI opponent, and it will only be playable in multiplayer mode.
4. The game will not have a tutorial section, and it will assume that players already know how to play Mancala.
5. The game will not have any database integration, and the game data will not be stored in any database.
6. The game will not have any social media integration, and there will be no option to share the game on social media platforms.
7. The Waiting Room feature will be implemented on the backend, but there will be no authentication and authorization mechanism for players.
8. The simultaneous Game Sessions feature will be implemented on the backend, but there will be no persistence of data after the backend is restarted.
9. The Mancala game will start when players are matched in the Waiting Room.

### Project Status
1. Game Session: The player's moves are not being synchronized for now. Players are able to play on the same page one by one
2. Multiplayer Feature: The Mancala game is implemented only on the client (frontend) for now. The multiplayer game feature is
   considered as the goal and to be implemented on the backend with player's moves synchronization.
3. Unit Testing: Unit tests are intended to be implemented for all the backend classes and methods. However, there are a few tests for methods in such classes as GlobalExceptionHandler, LocalDateTimeUtil, WaitingRoomService, PlayerController and GameSessionController
4. Frontend Unit Testing: Unit tests are intended to be implemented on the frontend methods containing any logic. Especially for the 
   temporary game implementation on the client until logic is moved to the backend. Also, the testing strategy for the frontend contains 
   an intention to test UI elements are being processed as expected using, e.g. React Testing Library

### Ideas
1. Allow players to choose the number of initial stones per pit
2. Allow players to choose the number of pits per player
3. Allow players to move in a clockwise direction instead of counterclockwise
4. Implement a timer for each player's turn
5. Implement a "hint" button to suggest a good move for the current player
6. Allow players to customize the game board
7. Implement a tutorial or help system to teach new players how to play
8. Gather and show stats after game is finished such as moves count, opponents' captured stones, etc 

# How to run

## Easy Way

1. Use the `docker-compose up` command in the project [root directory] to start the Docker containers with pre-configured images
2. You can modify environment variables or port mappings by editing the [docker-compose.yml] file and running Docker Compose again

## Build Manually

Clone the repository: `git clone https://github.com/vladmyers/mancala.git`

### Build all modules
1. Navigate to the project [root directory]
2. Build the project: `mvn clean package`

### Build modules separately
1. Navigate to the [mancala-frontend] directory: `cd mancala-frontend`
2. Install the necessary dependencies: `npm install`
3. Build the project: `npm run build`
4. Navigate to the [mancala-backend] directory: `cd ../mancala-backend`
5. Build the project: `mvn clean install`

## Usage

1. Switch to the [mancala-backend] directory: `cd ../mancala-backend`
2. Start the backend server: `mvn spring-boot:run`
3. Switch to the [mancala-frontend] directory: `cd ../mancala-frontend`
4. Start the frontend: `npm start`
5. Navigate to http://localhost:3000 in your web browser

# OpenAPI Specification

To view the API documentation after starting the backend server go to http://localhost:8080/swagger-ui.html

# Tech stack

## Common - across all modules

| Tech           | Description                                                        |
|----------------|--------------------------------------------------------------------|
| Docker         | Platform for developing, deploying, and running apps in containers |
| Docker Compose | Tool for defining and running multi-container Docker applications  |

## mancala-backend module
| Tech              | Description                                                        |
|-------------------|--------------------------------------------------------------------|
| Java 17           | Java latest LTS version                                            |
| Maven             | Build tool and dependency management                               |
| Spring Boot 3     | Spring-based application platform                                  |
| Lombok            | Boilerplate code generator                                         |
| JUnit5            | Java testing framework                                             |
| Checkstyle        | Static analysis tool to check for coding standards and conventions |
| OpenAPI (Swagger) | Designing, building, and documenting RESTful APIs toolset          |

## mancala-frontend module
| Tech          | Description                                                  |
|---------------|--------------------------------------------------------------|
| Node.js / npm | JavaScript runtime environment / package manager             |
| React.js      | JavaScript library for building user interfaces              |
| Typescript    | TypeScript is a strongly-typed superset of JavaScript        |
| Bootstrap     | CSS framework for designing and building responsive web apps |

## Using Checkstyle

Checkstyle is a static analysis tool that checks the project's Java source code for adherence to a set of coding standards and conventions.
It is used to ensure consistency and maintainability of the codebase.

### Project configuration

Checkstyle configuration files are located in the project's [config] directory.

| File                          | Description                                                            |
|-------------------------------|------------------------------------------------------------------------|
| [checkstyle.xml]              | Configuration file that defines the rules for checking the source code |
| [checkstyle-suppressions.xml] | Checkstyle suppression file that contains the suppressions             |

### Plugin for IntelliJ IDEA

Install the [CheckStyle plugin] for IntelliJ IDEA to run Checkstyle analysis directly from the IDE

# License

This project is licensed under the Apache License 2.0. See the [LICENSE] file for the full license text

[//]: # (Links)

[Intro]: #intro
[About the game]: #about-the-game
[How to run]: #how-to-run
[Build Manually]: #build-manually
[Build all modules]: #build-all-modules
[Build modules separately]: #build-modules-separately
[Easy Way]: #easy-way
[Usage]: #usage
[Project Scope and Limitations]: #project-scope-and-limitations
[Scope]: #scope
[Objectives]: #objectives
[Limitations]: #limitations
[Project Status]: #project-status
[Ideas]: #ideas
[OpenAPI Specification]: #openapi-specification
[Tech stack]: #tech-stack
[Common - across all modules]: #common---across-all-modules
[mancala-frontend module]: #mancala-frontend-module
[mancala-backend module]: #mancala-backend-module
[Using Checkstyle]: #using-checkstyle
[Project configuration]: #project-configuration
[Plugin for IntelliJ IDEA]: #plugin-for-intellij-idea
[License]: #license

[root directory]: .
[mancala-frontend]: mancala-frontend
[mancala-backend]: mancala-backend
[docker-compose.yml]: docker-compose.yml

[config]: config/checkstyle
[checkstyle.xml]: config/checkstyle/checkstyle.xml
[checkstyle-suppressions.xml]: config/checkstyle/checkstyle-suppressions.xml
[CheckStyle plugin]: https://plugins.jetbrains.com/plugin/1065-checkstyle-idea

[LICENSE]: LICENSE.txt

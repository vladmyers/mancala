# Mancala Game

## Table of Contents

* [Intro]
  * [About the game]
* [Getting Started]
  * [Installation]
    * [Build all modules]
    * [Build modules separately]
  * [Usage]
  * [Running with Docker Compose]
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

# Getting Started

## Installation

Clone the repository: `git clone https://github.com/vladmyers/mancala-game.git`

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

1. Start the backend server: `mvn spring-boot:run`
2. Start the frontend: `npm start`
3. Navigate to http://localhost:3000 in your web browser

## Running with Docker Compose

1. Open the [docker-compose.yml] file and make any necessary modifications to the environment variables or 
port mappings
2. Run the following command to start the Docker containers: `docker-compose up`

# Tech stack

## Common - across all modules

| Tech           | Description                                                        |
|----------------|--------------------------------------------------------------------|
| Docker         | Platform for developing, deploying, and running apps in containers |
| Docker Compose | Tool for defining and running multi-container Docker applications  |

## mancala-backend module
| Tech          | Description                                                        |
|---------------|--------------------------------------------------------------------|
| Java 17       | Java latest LTS version                                            |
| Maven         | Build tool and dependency management                               |
| Spring Boot 3 | Spring-based application platform                                  |
| Lombok        | Boilerplate code generator                                         |
| JUnit5        | Java testing framework                                             |
| Checkstyle    | Static analysis tool to check for coding standards and conventions |
| Swagger       | Designing, building, and documenting RESTful APIs toolset          |

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
[Getting Started]: #getting-started
[Installation]: #installation
[Build all modules]: #build-all-modules
[Build modules separately]: #build-modules-separately
[Running with Docker Compose]: #running-with-docker-compose
[Usage]: #usage
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

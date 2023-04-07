import React from 'react';
import {Link} from 'react-router-dom';
import {Button, Container, Nav, Navbar} from 'react-bootstrap';

type HeaderProps = {
    waitingRoomUuid?: string;
    onLeaveRoom?: () => void;
    gameSessionUuid?: string;
    onLeaveGame?: () => void;
};

const Header: React.FC<HeaderProps> = ({
                                           waitingRoomUuid,
                                           onLeaveRoom,
                                           gameSessionUuid,
                                           onLeaveGame
                                       }) => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg" style={{paddingBottom: '20px'}}>
            <Container>
                <Navbar.Brand>Mancala Game</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/">
                            Home
                        </Nav.Link>
                        <Nav.Link as={Link} to="/register">
                            Game
                        </Nav.Link>
                        <Nav.Link as={Link} to="/about">
                            About
                        </Nav.Link>
                    </Nav>
                    {waitingRoomUuid && (
                        <Button variant="outline-danger" onClick={onLeaveRoom} style={{marginLeft: '10px'}}>
                            Leave room
                        </Button>
                    )}
                    {gameSessionUuid && (
                        <Button variant="outline-danger" onClick={onLeaveGame} style={{marginLeft: '10px'}}>
                            Leave game
                        </Button>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;

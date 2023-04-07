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
        <Navbar bg="dark" variant="dark" expand="lg" style={{paddingBottom: '10px'}}>
            <Container>
                <Navbar.Brand>Mancala Game</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
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
                    <div className="d-flex">
                        {waitingRoomUuid && (
                            <Button variant="outline-danger" onClick={onLeaveRoom}>
                                Leave room
                            </Button>
                        )}
                        {gameSessionUuid && (
                            <Button variant="outline-danger" onClick={onLeaveGame}>
                                Leave game
                            </Button>
                        )}
                    </div>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;

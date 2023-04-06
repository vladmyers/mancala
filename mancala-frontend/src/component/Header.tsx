import React from 'react';
import {Link} from 'react-router-dom';
import {Container, Nav, Navbar, Button} from 'react-bootstrap';

type HeaderProps = {
    gameSessionUuid?: string;
    onLeaveGame?: () => void;
};

const Header: React.FC<HeaderProps> = ({gameSessionUuid, onLeaveGame}) => {
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

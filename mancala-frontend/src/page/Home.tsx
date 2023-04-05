import React from 'react';
import {Button, Card, Col, Row} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';

import Header from '../component/Header';
import Body from "../component/Body";

const Home = () => {
    useNavigate();
    const playerUuid = sessionStorage.getItem('playerUuid');

    return (
        <>
            <Header/>
            <Body>
                <Row className="justify-content-left">
                    <Col md={6} className="mb-3">
                        <Card>
                            <Card.Body>
                                <Card.Title>Play Mancala</Card.Title>
                                <Card.Text>
                                    Play Mancala with your friends online!
                                </Card.Text>
                                <Button variant="primary" href={playerUuid ? "/waiting-room" : "/register"}
                                        //TODO: move all styles in css files
                                        style={{backgroundColor: '#343A40', border: 'none', marginTop: '10px'}}>
                                    Play Now
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Body>
        </>
    );
};

export default Home;

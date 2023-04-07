import React from 'react';
import { Button, FormControl, InputGroup } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { ServiceLocator } from '../service/ServiceLocator';
import PlayerService from '../service/PlayerService';
import Header from '../component/Header';
import Body from '../component/Body';

const Registration = () => {
    const playerService: PlayerService = ServiceLocator.get('playerService')!;

    const [username, setUsername] = React.useState('');
    const [registrationResult, setRegistrationResult] = React.useState('');
    const navigate = useNavigate();

    // check if playerUuid is present in sessionStorage
    React.useEffect(() => {
        const playerUuid = sessionStorage.getItem('playerUuid');
        if (playerUuid) navigate('/');
    }, [navigate]);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        playerService
            .register(username)
            .then((response) => {
                const player = response.result!
                sessionStorage.setItem('playerUuid', player.uuid);
                sessionStorage.setItem('playerUsername', player.username);

                setRegistrationResult(`Registration is successful for player '${response.result?.username}'. Redirecting...`);

                setTimeout(() => navigate('/waiting-room'), 3000);
            })
            .catch((error) => {
                setRegistrationResult(`Registration failed: ${error.message}`);
            });
    };

    return (
        <>
            <Header />
            <Body>
                <div className="d-flex justify-content-center">
                    <form onSubmit={handleSubmit} style={{ maxWidth: '800px', width: '100%' }}>
                        <div className="form-group row">
                            <label className="col-4 col-form-label" htmlFor="username">
                                Enter your username
                            </label>
                            <div className="col-8">
                                <InputGroup>
                                    <FormControl
                                        id="username"
                                        name="username"
                                        placeholder="Ben"
                                        type="text"
                                        required
                                        value={username}
                                        onChange={(e) => setUsername(e.target.value)}
                                    />
                                </InputGroup>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-4 col-8">
                                <Button
                                    name="submit"
                                    type="submit"
                                    className="btn btn-primary"
                                    style={{ backgroundColor: '#343a40', borderColor: '#343a40', marginTop: '30px' }}
                                >
                                    Submit
                                </Button>
                            </div>
                        </div>
                    </form>
                </div>
                {registrationResult && (
                    <div className="form-group row" style={{ marginTop: '20px' }}>
                        <div className="offset-4 col-8">
                            <div
                                className={`alert ${registrationResult.includes('failed') ? 'alert-danger' : 'alert-success'}`}
                                role="alert"
                            >
                                {registrationResult}
                            </div>
                        </div>
                    </div>
                )}
            </Body>
        </>
    );
};

export default Registration;

import { useState } from 'react';
import '@/styles/pages-styles/LoginPage.css';
import type { LoginUser } from '@/types/Users/LoginUser';
import { getUserByUsername, handleLogin } from '@/api/service/UserService';
import { useNavigate } from 'react-router-dom';
import { TextField } from '@mui/material';
import { useAuth } from '@/context/AuthContext';

const LoginPage = () => {
    const [user, setUser] = useState<LoginUser>({password: "", username: ""});
    const [error, setError] = useState("");
    const { setSession } = useAuth();
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);

    const onSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setIsDisabled(true);
    
        console.log(user);
        const result = await handleLogin(user);
        console.log(result);
        if (result.data === "success") {
            const userData = await getUserByUsername(user.username);
            setSession({ 
                user: userData.data, 
                isLoggedIn: true 
            });
            navigate("/home", { replace: true });
        } else {
            setError(result.data);
            console.log(error);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setUser(prev => ({ ...prev, [name]: value }));
    };

    const togglePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        setShowPassword(e.target.checked);
    };

    return (
        <div className="container">
            <div className="formContainer">
                <p className='createAccountHeading'>Sign in to our App</p>
                <form className="form" onSubmit={onSubmit}>
                    <div className='textfieldsContainer'>
                        <TextField type='text' label="Username" 
                                        id='username'
                                        name='username'
                                        value={user.username}
                                        onChange={handleChange} className='usernameTextField'/>
                        <div className='passwordContainer'>
                            <div className='passwordWrapper'>
                                <TextField
                                    type={showPassword ? 'text' : 'password'}
                                    label="Password"
                                    id='password'
                                    name='password'
                                    value={user.password}
                                    onChange={handleChange}
                                    className='passwordTextField'
                                    fullWidth
                                />
                                <input
                                    type='checkbox'
                                    className='checkboxInput'
                                    checked={showPassword}
                                    onChange={togglePassword}
                                />
                            </div>
                        </div>
                    </div>
                    

                    <div className='buttonContainer'>
                        <button className='submitButton' disabled={isDisabled} type="submit">Login</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default LoginPage;

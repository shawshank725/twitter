import { useState } from 'react';
import type { NewUser } from '@/types/Users/NewUser';
import { emailAvailable, handleRegister, usernameAvailable } from '@api/service/UserService';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import '@styles/pages-styles/RegisterPage.css';
import { TextField } from '@mui/material';


const RegisterPage = () => {
    const [newUser, setNewUser] = useState<NewUser>({ username: "", name: "", email: "", password: "", });
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);

    const isFormValid =
        newUser.username.trim() !== "" &&
        newUser.name.trim() !== "" &&
        newUser.email.trim() !== "" &&
        newUser.password.trim() !== "";

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewUser(prev => ({ ...prev, [name]: value }));
    };

    const validatePassword = (password: string): boolean => {
        const regex =
            /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&\-+=()])(?=\S+$).{8,20}$/;

        return password != null && regex.test(password);
    };


    const togglePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        setShowPassword(e.target.checked);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsDisabled(true);

        const isEmailAvailable = await emailAvailable(newUser.email.trim());
        const isUserAvailable = await usernameAvailable(newUser.username.trim());
        console.log(isEmailAvailable);
        console.log(isUserAvailable);

        if (isEmailAvailable == false) {
            setError("Email already taken.");
            setIsDisabled(false);
            return;
        }
        if (isUserAvailable == false) {
            setError("Username already taken.");
            setIsDisabled(false);
            return;
        }
        if (!validatePassword(newUser.password.trim())) {
            setError("Password must contain uppercase, lowercase, number, and special character, and be 8-20 characters long.");
            setIsDisabled(false);
            return;
        }

        const user: NewUser = {
            username: newUser.username.trim(),
            name: newUser.name.trim(),
            email: newUser.email.trim(),
            password: newUser.password
        };

        console.log(user);
        
        setError("");
        const response = await handleRegister(user);
        console.log("this is the fucking response: " + response);

        if (response.data == "success") {
            toast("You have registered successfully.",);
            navigate("/auth", { replace: true });
        }
        else {
            toast("Registration failed. Please try again later");
        }
    };


    return (
        <div className="container">
            <div className="formContainer">
                <p className='createAccountHeading'>Create your account</p>
                <form className="form" onSubmit={handleSubmit}>
                    <div className='textfieldsContainer'>
                        <TextField type='text' label="Username"
                            id='username'
                            name='username'
                            value={newUser.username}
                            onChange={handleChange} className='usernameTextField' />

                        <TextField type='text' label="Name"
                            id='name'
                            name='name'
                            value={newUser.name}
                            onChange={handleChange} className='usernameTextField' />

                        <TextField type='text' label="Email"
                            id='email'
                            name='email'
                            value={newUser.email}
                            onChange={handleChange} className='usernameTextField' />
                        <div className='passwordContainer'>
                            <div className='passwordWrapper'>
                                <TextField
                                    type={showPassword ? 'text' : 'password'}
                                    label="Password"
                                    id='password'
                                    name='password'
                                    value={newUser.password}
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
                        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <button className='submitButton' disabled={isDisabled || !isFormValid} type="submit">Create Account</button>
                        </div>
                    </div>

                    <p
                        style={{
                            color: "red",
                            width: "200px",
                            textAlign: "center",
                            justifySelf: 'center',
                            wordBreak: "break-word",
                            overflowWrap: "break-word"
                        }}
                    >
                        {error}
                    </p>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;

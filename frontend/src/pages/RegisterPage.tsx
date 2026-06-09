import { useState } from 'react';
import type { NewUser } from '@/types/Users/NewUser';
import { emailAvailable, handleRegister, usernameAvailable } from '@api/service/UserService';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import '@styles/pages-styles/RegisterPage.css';
import { backgroundPhotoUrlConstant, profilePhotoUrlConstant } from '@constants/PhotoUrls';
import type { User } from '@/types/Users/User';


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
        const currentDate = new Date().toISOString().split("T")[0];

        const isEmailAvailable = await emailAvailable(newUser.email);
        const isUserAvailable = await usernameAvailable(newUser.username);

        if (isEmailAvailable == false) {
            setError("Email already taken.");
            return;
        }
        if (isUserAvailable == false) {
            setError("Username already taken.");
            return;
        }
        if (!validatePassword(newUser.password)) {
            setError("Password must contain uppercase, lowercase, number, and special character, and be 8-20 characters long.");
            return;
        }

        const user: User = {
            username: newUser.username,
            name: newUser.name,
            email: newUser.email,
            password: newUser.password,
            backgroundPhoto: backgroundPhotoUrlConstant,
            profilePhoto: profilePhotoUrlConstant,
            joinedDate: currentDate,
            id: 0,
            bio: null,
            website: null,
            location: null,
            role: ''
        };
        setError("");
        const response = await handleRegister(user);
        if (response.data == "success") {
            console.log("the registration response is " + response);
            toast("You have registered successfully.", );
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
                    <div className='formTableContainer'>
                        <table>
                        <tbody>
                            <tr>
                                <td><label htmlFor='username' className='label'>Username:</label></td>
                                <td>
                                    <input
                                        type='text'
                                        id='username'
                                        name='username'
                                        className='inputField'
                                        value={newUser.username}
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            
                            <tr>
                                <td><label htmlFor='name' className='label'>Name:</label></td>
                                <td>
                                    <input
                                        type='text'
                                        id='name'
                                        name='name'
                                        className='inputField'
                                        value={newUser.name}
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td><label htmlFor='email' className='label'>Email:</label></td>
                                <td>
                                    <input
                                        type='email'
                                        id='email'
                                        name='email'
                                        className='inputField'
                                        value={newUser.email}
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td><label htmlFor='password' className='label'>Password:</label></td>
                                <td>
                                    <input
                                        type={showPassword ? 'text' : 'password'}
                                        id='password'
                                        name='password'
                                        className='inputField'
                                        value={newUser.password}
                                        onChange={handleChange}
                                    />
                                    <input type='checkbox' checked={showPassword} onChange={togglePassword} />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </div>

                    <div className='buttonContainer'>
                        <div style={{display: 'flex',justifyContent:'center', alignItems:'center'}}>
                            <button className='submitButton'disabled={isDisabled || !isFormValid} type="submit">Create Account</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;

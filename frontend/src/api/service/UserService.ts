import axios, { type AxiosResponse } from 'axios';
import type { NewUser } from '@/types/Users/NewUser';
import type { LoginUser } from '@/types/Users/LoginUser';
import type { User } from '@/types/Users/User';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';

export const usernameAvailable = async (username: string): Promise<boolean> => {
    try {
        const response = await axios.post(
            `${SPRING_BOOT_LOCALHOST}/user/usernameAvailable`,
            username,
            {
                withCredentials: true,
                headers: {
                    "Content-Type": "application/json",
                }
            }
        );

        return response.data;
    } catch (err) {
        console.error(err);
        return false;
    }
};

export const emailAvailable = async (email: string): Promise<boolean> => {
    try {
        const response = await axios.post(
            `${SPRING_BOOT_LOCALHOST}/user/emailAvailable`,
            email,
            {
                withCredentials: true,
                headers: {
                    "Content-Type": "application/json",
                }
            }
        );

        return response.data;
    } catch (err) {
        console.error(err);
        return false;
    }
};


export async function getUserInformation() {
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/user/me`, {
        withCredentials: true
    });
}

export async function getUserByUsername(username:string){
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/user/${username}`,{
        withCredentials: true
    });
}

export async function getUserByUserId(userId: number): Promise<AxiosResponse<User>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/user/getUserByUserId`,{
        params: { userId },
        withCredentials: true
    });
}


//REGISTERING NEW USER
export async function handleRegister(newUser: NewUser){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/auth/register`, newUser );
}


//LOGGING IN THE USER
export async function handleLogin(loginUser: LoginUser){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/auth/login`, loginUser, {
        withCredentials: true
    } );
}

//UPDATING THE EXISTING USER
export async function updateUserProfile(existingUser: User | null){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/user/updateUserProfile`, existingUser, {
        withCredentials: true
    } );
}

// UPDATING THE USER NAME
export async function updateUsername(oldUsername: string, newUsername: string, password: string) {
  return await axios.post(
    `${SPRING_BOOT_LOCALHOST}/user/updateUsername`,
    null, 
    {
      params: { oldUsername, newUsername, password },
        withCredentials: true
    }
  );
}

// UPDATING THE PASSWORD
export async function changePassword(username: string, oldPassword: string, newPassword: string) {
  return await axios.post(
    `${SPRING_BOOT_LOCALHOST}/user/changePassword`,
    null, 
    {
      params: { username, oldPassword, newPassword },
        withCredentials: true
    }
  );
}

export async function deleteUserAccount(username: string, password: string) {
  return await axios.post(
    `${SPRING_BOOT_LOCALHOST}/user/deleteAccount`,
    null, 
    {
      params: { username, password },
        withCredentials: true
    }
  );
}

export const getCurrentUserInformation = async () => {
    const response = await axios.get(`${SPRING_BOOT_LOCALHOST}/user/me`,{
        withCredentials: true
    });

    return response.data;
};
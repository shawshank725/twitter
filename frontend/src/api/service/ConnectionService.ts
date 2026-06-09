import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { NewConnectionEntity } from '@/types/Connections/ConnectionEntity';
import type { FollowerFolloweeDTO } from '@/types/Connections/FollowerFolloweeDTO';

export async function addConnection(newConnectionEntity: NewConnectionEntity){
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/connection/addConnection`, 
        newConnectionEntity,
        {
            withCredentials: true
        }
    );
}

export async function deleteConnectionByBothIds(followeeId: number , followerId: number){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/connection/deleteConnectionEntityByFollowerAndFolloweeId`, 
        null,
        {
        withCredentials: true,
        params: { followerId, followeeId }
        }
    );
}

export async function getUsersConnection(userId: number): Promise<AxiosResponse<FollowerFolloweeDTO>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/connection/getUserConnections`, 
        {
            
            withCredentials: true,
            params: { userId }
        }
    );
}

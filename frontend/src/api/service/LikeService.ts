import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { NewLikeEntity } from '@/types/Likes/NewLikeEntity';
import type { LikeEntity } from '@/types/Likes/LikeEntity';

export async function addLike(newLikeEntity: NewLikeEntity){
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/post/addLike`, 
        newLikeEntity,
        {
            withCredentials: true,
        }
    )
}

export async function getLikesByPostId(postId: number) : Promise<AxiosResponse<LikeEntity[]>>{
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/post/getLikesByPostId`, 
        {
            withCredentials: true,
            params: { postId }
        }
    );
}

export async function deleteLikeEntity(likedPostId: number, likedByUserId: number){
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/post/deleteLikeEntity`, 
        null, 
        {
            withCredentials: true,
            params: { likedPostId, likedByUserId }
        }
    );
}

export async function getLikeEntityByUserId(userId: number){
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/post/getLikeEntityByUserId`, 
        {            
            withCredentials: true,
            params: { userId }
        }
    );
}

// GETTING POST LIKES COUNT
export async function getPostLikesCount(postId: number){
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/post/getPostLikesCount`, 
        {            
            withCredentials: true,
            params: { postId }
        }
    );
}


// GETTING LIKED POSTS OF A USER 
export async function getLikedPosts(userId: number ){
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/post/getLikedPosts`, 
        {
            withCredentials: true,
            params: { userId }
        }
    );
}
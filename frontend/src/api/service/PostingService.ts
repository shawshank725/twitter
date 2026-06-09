import axios, { type AxiosResponse } from 'axios';
import type { NewPostEntity, PostEntity } from '@/types/Posts/PostEntity';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';

export async function saveNewPost(newPostEntity: NewPostEntity){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/post/addPost`, 
        newPostEntity ,{
            withCredentials: true
        }
    );
}

export async function getUsersPost(userId: number) {
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/post/getUsersPosts`, {
        params: {userId},
        withCredentials: true
    });
}

export async function deletePostByPostId(postId: number){
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/post/deletePost`,
        {}, {
            params: {postId},
            withCredentials: true
        }
    )
}


export async function getPostByPostId(postId: number) : Promise<AxiosResponse<PostEntity>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/post/getPostByPostId`, {
        params: {postId},
        withCredentials: true
    });
}

export async function getRepliesToPost(postId: number): Promise<AxiosResponse<PostEntity[]>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/post/getRepliesToPost`, {
        params: {postId},
        withCredentials: true
    });
}

export async function deleteAllUsersPosts(userId: number) {
    return await axios.post(`${SPRING_BOOT_LOCALHOST}/post/deletePostsOfUser`,
        {},{
            params: {userId},
            withCredentials: true
        }
    )
}
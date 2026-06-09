import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { PostEntity } from '@/types/Posts/PostEntity';

// to get count 
export async function getQuotedCount(postId: number): Promise<AxiosResponse<number>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/quote/getQuotedCount`, {
        params: {postId},
        
        withCredentials: true
    });
}

// to get the posts (quote posts)
export async function getQuotedPosts(postId: number): Promise<AxiosResponse<PostEntity[]>>{
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/quote/getQuotedPosts`, {
        params: {postId},
       
        withCredentials: true
    });
}
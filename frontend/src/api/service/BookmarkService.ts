import axios, { type AxiosResponse } from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';
import type { NewBookmarkEntity } from '@/types/Bookmarks/NewBookmarkEntity';
import type { BookmarkEntity } from '@/types/Bookmarks/BookmarkEntity';
import type { PostEntity } from '@/types/Posts/PostEntity';

export async function addBookmark(newBookmarkEntity: NewBookmarkEntity): Promise<AxiosResponse<BookmarkEntity>>{
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/bookmark/addBookmark`, 
        newBookmarkEntity,
        {
            withCredentials: true
        }
    )
}

export async function removeBookmarkEntity(bookmarkedPostId: number, bookmarkedByUserId: number) {
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/bookmark/removeBookmark`, 
        null, 
        {
            withCredentials:true,
            params: { bookmarkedPostId, bookmarkedByUserId }
        }
    );
}

export async function getAllBookmarksByUserId(userId: number) : Promise<AxiosResponse<BookmarkEntity[]>>{
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/bookmark/getAllBookmarksByUserId`, 
        {
            withCredentials: true,
            params: { userId }
        }
    );

}


export async function getPostBookmarkCount(postId: number) : Promise<AxiosResponse<number>>{
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/bookmark/getPostBookmarkCount`, 
        {
            withCredentials: true,
            params: { postId }
        }
    );
}

export async function getPostsFromBookmarks(userId: number): Promise<AxiosResponse<PostEntity[]>>{
    return await axios.get(
        `${SPRING_BOOT_LOCALHOST}/bookmark/getPostEntityBasedOnBookmarks`, 
        {
            withCredentials: true,
            params: { userId }
        }
    );
}
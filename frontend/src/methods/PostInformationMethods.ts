import type { LikeEntity } from "@/types/Likes/LikeEntity";
import type { BookmarkEntity } from "@/types/Bookmarks/BookmarkEntity";
import { toast } from "react-toastify";
import { deletePostByPostId } from "@/api/service/PostingService";
import { queryClient } from "@/main";
import type { User } from "@/types/Users/User";
import { deleteLikeEntity, addLike } from "@/api/service/LikeService";
import type { PostEntity } from "@/types/Posts/PostEntity";
import type { AxiosResponse } from "axios";
import type { UseQueryResult } from "@tanstack/react-query";
import { addBookmark, removeBookmarkEntity } from "@/api/service/BookmarkService";

export function hasUserLikedPost( likesList: LikeEntity[] , postId: number): boolean {
  if (!likesList) return false;
  return likesList.some(like =>
    like.likedPost && like.likedPost.postId === postId
  );
}

export function hasUserBookmarkedPost(bookmarksList: BookmarkEntity[] | undefined, authUserId: number, postId: number): boolean {
    if (!bookmarksList) return false;
    return bookmarksList.some(bookmark =>
        bookmark.bookmarkedByUserId === authUserId &&
        bookmark.bookmarkedPost.postId === postId
    );
}

export const deletePost = async(postId: number, userEntity: User, 
  setShowMoreSettings:(value: boolean)=>void, refetch?:()=>void) => {
  try {
      await deletePostByPostId(postId);
      toast(`Post deleted.`);
      await queryClient.invalidateQueries({queryKey: ['posts', userEntity.userId]});
      console.log("Invalidated posts query for user:", userEntity.userId);
      setShowMoreSettings(false);
      refetch?.();
  }
  catch(error){
      console.log("Error from this line - " , error);
  }
}

export const addRemoveLike = async (postEntity:PostEntity, 
  authUser:User, userHasLikedPost: boolean, 
  refetchPostLikesCount:()=> void, userLikedPostsResult: UseQueryResult<AxiosResponse<LikeEntity[]>>) => {
  try {
    if (userHasLikedPost) {
      await deleteLikeEntity(postEntity.postId, authUser!.userId);
      refetchPostLikesCount();
      userLikedPostsResult.refetch();
    }
    else {
      // ADD THE LIKE 
      const date = new Date();
      await addLike({
        likedByUserId: authUser!.userId,
        likedPost: postEntity,
        likedAt: date.toISOString(),
      });
      refetchPostLikesCount();
      userLikedPostsResult.refetch();
    }
  }
  catch (error) { console.log(error); }
}

export const addRemoveBookmark = async (postEntity:PostEntity, 
  authUser:User, userHasBookmarkedPost: boolean, 
  refetchPostBookmarksCount:()=> void, userBookmarkedPostsResult: UseQueryResult<AxiosResponse<BookmarkEntity[]>>) => {

    try {
    if (userHasBookmarkedPost) {
      await removeBookmarkEntity(postEntity.postId, authUser!.userId);
      refetchPostBookmarksCount();
      userBookmarkedPostsResult.refetch();
    }
    else {
      // ADD THE LIKE 
      const date = new Date();
      const result = await addBookmark({
        bookmarkedByUserId: authUser!.userId,
        bookmarkedPost: postEntity,
        bookmarkedAt: date.toISOString(),
      });
      console.log(result);
      refetchPostBookmarksCount();
      userBookmarkedPostsResult.refetch();
    }
  }
  catch (error) { console.log(error); }
}
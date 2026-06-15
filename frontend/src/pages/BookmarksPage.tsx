import { ArrowLeft } from "lucide-react";
import "@/styles/pages-styles/BookmarksPage.css";
import { useAuth } from "@context/AuthContext";
import { useNavigate } from "react-router-dom";
import { useGetBookmarkPosts, useGetUsersBookmarks } from "@/api/query/BookmarksQueries";
import PostCard from "@components/posts/PostCard";
import ErrorMessage from "@components/ErrorMessage";
import type { PostEntity } from "@/types/Posts/PostEntity";
import { useGetPostLikesByUser } from "@/api/query/LikeQueries";
import { useEffect } from "react";

export default function BookmarksPage() {
    const { session } = useAuth();
    const authUser = session.user;
    const navigate = useNavigate();
    
    const userLikedPostsResult = useGetPostLikesByUser(authUser?.userId!);
    const userBookmarkedPostsResult = useGetUsersBookmarks(authUser?.userId!);
    const {data: bookmarks} = useGetBookmarkPosts(authUser?.userId!);
    
    useEffect(() => {
        if (authUser) {
            document.title = `Your bookmarks`;
        }
    }, [authUser]);

    return (
        <div className="bookmarksPageContainer">
            <div className="bookmarkHeadingContainer">
                <div className="arrowLeftBookmarkViewerHeaderContainer">
                    <ArrowLeft className="arrowLeftBookmarkViewerHeader" size={20}
                        onClick={() => {if (window.history.length > 1) { navigate(-1); } 
                                        else {navigate('/home'); }  }}
                    />
                </div>
                <span className="bookmarkHeaderHeadingSpan">Bookmarks</span>
            </div>
            {
                authUser && bookmarks?.length! > 0 && (
                    bookmarks?.map((bookmarkPost: PostEntity) => (
                        <PostCard userId={authUser.userId} 
                        postEntity={bookmarkPost} 
                        userLikedPostsResult={userLikedPostsResult}
                        userBookmarkedPostsResult={userBookmarkedPostsResult} 
                        key={bookmarkPost.postId}/>
                    ))
                )
            }
            {
                bookmarks?.length === 0 && (
                    <ErrorMessage title="No Bookmarks." message="Try adding some bookmarks. They'll show up here."/>
                )
            }
        </div>
    )
}
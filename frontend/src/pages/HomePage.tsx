import '@styles/universal.css';
import '@styles/pages-styles/HomePage.css';
import { useEffect } from 'react';
import { useAuth } from '@context/AuthContext';
import PostingArea from '@components/posts/PostingArea';
import PostCard from '@components/posts/PostCard';
import { useGetTimeline } from '@/api/query/PostQueries';

export default function HomePage() {
  useEffect(() => {
    document.title = "Home";
  }, []);

  const { session } = useAuth();
  const authUser = session.user;

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage
  } = useGetTimeline();

  const posts =
    data?.pages.flatMap(page => page.content) ?? [];

  return (
    <div className="homePageContainer">
      <PostingArea userInfo={authUser} />

      {authUser &&
        posts.map((post) => (
          <PostCard
            key={post.postId}
            userId={post.userId}
            postEntity={post}
          />
        ))}

      <div
        ref={(node) => {
          if (!node) return;

          const observer = new IntersectionObserver((entries) => {
            if (
              entries[0].isIntersecting &&
              hasNextPage &&
              !isFetchingNextPage
            ) {
              fetchNextPage();
            }
          });

          observer.observe(node);

          return () => observer.disconnect();
        }}
      />
    </div>

  );
}
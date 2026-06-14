import { Routes, Route, Navigate } from "react-router-dom";
import { useEffect } from "react";
import LandingPage from "@pages/LandingPage";
import LoginPage from "@pages/LoginPage";
import RegisterPage from "@pages/RegisterPage";
import HomePage from "@pages/HomePage";
import ProfilePage from "@pages/ProfilePage";
import SettingsPage from "@pages/SettingsPage";
import Layout from "@pages/Layout";
import ProtectedRoute from "@pages/ProtectedRoute";
import { useAuth } from "@context/AuthContext";
import PublicRoute from "@pages/PublicRoute";
import ConnectionPage from "@pages/ConnectionPage";
import PostViewerPage from "@pages/PostViewerPage";
import BookmarksPage from "@pages/BookmarksPage";
import NotificationPage from "@pages/NotificationPage";
import { connectSocket} from "@socket/SocketClient";
import subscribeToNotifications from "@socket/SocketClient";
import SearchPage from "@pages/SearchPage";
import QuoteRetweetPage from "./pages/QuoteRetweetPage";
import ChangeUsername from "./pages/settings/ChangeUsername";
import ChangePassword from "./pages/settings/ChangePassword";

function App() {
  
  const {session, isLoading } = useAuth();
  const authUser = session.user;
  useEffect(()=> {
    if (authUser) {
      connectSocket(() => {
        console.log(`Subscribing to notifications for user ID: ${authUser.userId}`);
        subscribeToNotifications(authUser.userId);
      });
    } else {
      console.log("No authUser or token, skipping WebSocket connection");
    }
  }, [authUser]);
  

  if (isLoading) return <div>Loading...</div>;

  return (
    <Routes>
      <Route path="/auth" element={ <PublicRoute>
            <LandingPage />
          </PublicRoute>
        }
      />
      <Route  path="/login"  element={  <PublicRoute>
            <LoginPage />
          </PublicRoute>
        }
      />
      <Route  path="/register"  element={  <PublicRoute>
            <RegisterPage />
          </PublicRoute>
        }
      />
      <Route  path="/"  element={ <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/home" replace />} />
        <Route path="home" element={<HomePage />} />
        <Route path="settings" element={<SettingsPage />} />
        <Route path="settings/changeUsername" element={<ChangeUsername />} />
        <Route path="settings/changePassword" element={<ChangePassword />} />

        <Route path=":username" element={<ProfilePage />} />
        <Route path=":username/:connectionType" element={<ConnectionPage />} />

        <Route path="post/:postId" element={<PostViewerPage />} />
        <Route path="post/:postId/quotePosts" element={<QuoteRetweetPage />} />
        <Route path="bookmarks" element={<BookmarksPage />} />
        <Route path="notifications" element={<NotificationPage />} />
        <Route path="search" element={<SearchPage />} />

      </Route>
    </Routes>
  );
}

export default App;
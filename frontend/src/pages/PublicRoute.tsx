import { Navigate } from "react-router-dom";
import { useAuth } from "@context/AuthContext";
import type { JSX } from "react";

export default function PublicRoute({ children }: { children: JSX.Element }) {
  const { session } = useAuth();

  // If user is logged in, redirect to home
  if (session.isLoggedIn) {
    return <Navigate to="/home" replace />;
  }

  return <div>{children}</div>;
}

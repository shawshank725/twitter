import { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import type { User } from "@/types/Users/User";
import { getCurrentUserInformation } from "@/api/service/UserService";


interface Session {
  user: User | null;
  isLoggedIn: boolean;
}

interface AuthContextType {
  session: Session;
  setSession: (session: Session ) => void;
  isLoading: boolean;
  setIsLoading: (isLoading: boolean) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [session, setSession] = useState<Session>({
    user: null,
    isLoggedIn: false,
  });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const restoreSession = async () => {
      try {
        const user = await getCurrentUserInformation();
        if (user) {
          setSession({ user, isLoggedIn: true });
        }
      } catch {
        // No valid session, stay logged out
        setSession({ user: null, isLoggedIn: false });
      } finally {
        setIsLoading(false);
      }
    };

    restoreSession();
  }, []);
  
  return (
    <AuthContext.Provider
      value={{ 
        session, 
        setSession, 
        isLoading, 
        setIsLoading 
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
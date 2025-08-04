"use client"

import {createContext, useContext, useState, type ReactNode, useEffect} from "react"
import { useRouter } from "next/navigation"

interface AuthContextType {
  isAuthenticated: boolean
  login: (token: any) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const router = useRouter()

  useEffect(() => {
    const token = localStorage.getItem("token");
    if(token != null)
    {
      setIsAuthenticated(true)
    }
  }, [isAuthenticated]);


  const login = (token: string) => {
    localStorage.setItem("token", token)
    setIsAuthenticated(true)
    router.push("/dashboard")
  }

  const logout = () => {
    localStorage.removeItem("token")
    setIsAuthenticated(false)
    router.push("/login")
  }

  return <AuthContext.Provider value={{ isAuthenticated, login, logout }}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider")
  }
  return context
}

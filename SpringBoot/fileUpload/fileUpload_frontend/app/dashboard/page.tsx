"use client"

import { useEffect } from "react"
import { useRouter } from "next/navigation"
import { useAuth } from "@/contexts/auth-context"
import FileBrowser from "@/components/file-browser"
import { Skeleton } from "@/components/ui/skeleton"

export default function DashboardPage() {
  const { isAuthenticated } = useAuth()
  const router = useRouter()

  useEffect(() => {
      if (!isAuthenticated) {
          router.push("/login")
      }else {
          router.push("/dashboard")
      }
  }, [isAuthenticated, router])
  if (!isAuthenticated) {
    return (
      <div className="flex flex-col gap-6">
        <header>
          <Skeleton className="h-9 w-48" />
          <Skeleton className="h-5 w-64 mt-2" />
        </header>
        <Skeleton className="w-full h-[400px]" />
      </div>
    )
  }

  return (
    <div className="flex flex-col gap-6">
      <header>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
        <p className="text-muted-foreground">파일과 폴더를 관리하세요.</p>
      </header>
      <FileBrowser />
    </div>
  )
}

"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { File, FolderKanban, LogIn, UserPlus, LogOut } from "lucide-react"
import { cn } from "@/lib/utils"
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip"
import { useAuth } from "@/contexts/auth-context"
import { Button } from "./ui/button"

export default function AppSidebar() {
  const pathname = usePathname()
  const { isAuthenticated, logout } = useAuth()

  const navItems = isAuthenticated
    ? [{ href: "/dashboard", icon: FolderKanban, label: "Dashboard" }]
    : [
        { href: "/login", icon: LogIn, label: "Login" },
        { href: "/sign-up", icon: UserPlus, label: "Sign Up" },
      ]

  return (
    <aside className="hidden w-14 flex-col border-r bg-background sm:flex">
      <nav className="flex flex-col items-center gap-4 px-2 sm:py-5 flex-1">
        <TooltipProvider>
          <Link
            href="/dashboard"
            className="group flex h-9 w-9 shrink-0 items-center justify-center gap-2 rounded-full bg-primary text-lg font-semibold text-primary-foreground md:h-8 md:w-8 md:text-base"
          >
            <File className="h-4 w-4 transition-all group-hover:scale-110" />
            <span className="sr-only">File Manager</span>
          </Link>
          {navItems.map((item) => (
            <Tooltip key={item.label}>
              <TooltipTrigger asChild>
                <Link
                  href={item.href}
                  className={cn(
                    "flex h-9 w-9 items-center justify-center rounded-lg text-muted-foreground transition-colors hover:text-foreground md:h-8 md:w-8",
                    pathname === item.href && "bg-accent text-accent-foreground",
                  )}
                >
                  <item.icon className="h-5 w-5" />
                  <span className="sr-only">{item.label}</span>
                </Link>
              </TooltipTrigger>
              <TooltipContent side="right">{item.label}</TooltipContent>
            </Tooltip>
          ))}
        </TooltipProvider>
      </nav>
      {isAuthenticated && (
        <nav className="mt-auto flex flex-col items-center gap-4 px-2 sm:py-5">
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger asChild>
                <Button
                  onClick={logout}
                  variant="ghost"
                  size="icon"
                  className="h-9 w-9 text-muted-foreground transition-colors hover:text-foreground md:h-8 md:w-8"
                >
                  <LogOut className="h-5 w-5" />
                  <span className="sr-only">Logout</span>
                </Button>
              </TooltipTrigger>
              <TooltipContent side="right">Logout</TooltipContent>
            </Tooltip>
          </TooltipProvider>
        </nav>
      )}
    </aside>
  )
}

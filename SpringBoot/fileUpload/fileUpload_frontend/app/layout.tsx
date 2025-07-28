import type React from "react"
import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"
import { ThemeProvider } from "@/components/theme-provider"
import AppSidebar from "@/components/app-sidebar"
import { AuthProvider } from "@/contexts/auth-context"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: "File Manager",
  description: "Manage your files and folders.",
    generator: 'v0.dev'
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="ko" suppressHydrationWarning>
      <body className={inter.className}>
        <AuthProvider>
          <ThemeProvider attribute="class" defaultTheme="system" enableSystem disableTransitionOnChange>
            <div className="flex min-h-screen w-full bg-muted/40">
              <AppSidebar />
              <main className="flex-1 flex flex-col p-4 sm:p-6">{children}</main>
            </div>
          </ThemeProvider>
        </AuthProvider>
      </body>
    </html>
  )
}

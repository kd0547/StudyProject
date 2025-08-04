"use client"

import type React from "react"

import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useAuth } from "@/contexts/auth-context"

export default function LoginPage() {
  const { login } = useAuth()

  const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL



  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault()

    const from = event.currentTarget as HTMLFormElement;
    const email = (from.querySelector("#email") as HTMLInputElement).value;
    const password = (from.querySelector("#password") as HTMLInputElement).value;

    try {
      const response = await fetch(`${baseUrl}/api/v1/auth/login`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({email, password})
          }
      )

      if (!response.ok) {
        if(response.status == 401) {
          throw new Error("로그인 실패");
        }
      }

      const data = await response.json();
      const token = data["authorization"];
      login(token)

    } catch (err) {
      alert("로그인 실패");

    }
  }

  return (
    <div className="flex items-center justify-center h-full">
      <Card className="mx-auto max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">로그인</CardTitle>
          <CardDescription>이메일과 비밀번호를 입력하여 계정에 로그인하세요.</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleLogin} className="grid gap-4">
            <div className="grid gap-2">
              <Label htmlFor="email">이메일</Label>
              <Input id="email" type="email" placeholder="m@example.com" required />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="password">비밀번호</Label>
              <Input id="password" type="password" required />
            </div>
            <Button type="submit" className="w-full">
              로그인
            </Button>
            <Button variant="outline" className="w-full bg-transparent">
              GitHub로 로그인
            </Button>
          </form>
          <div className="mt-4 text-center text-sm">
            계정이 없으신가요?{" "}
            <Link href="/sign-up" className="underline">
              회원가입
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}

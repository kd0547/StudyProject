import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export default function SignUpPage() {
  return (
    <div className="flex items-center justify-center h-full">
      <Card className="mx-auto max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">회원가입</CardTitle>
          <CardDescription>아래 정보를 입력하여 계정을 생성하세요.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4">
            <div className="grid grid-cols-2 gap-4">
              <div className="grid gap-2">
                <Label htmlFor="first-name">성</Label>
                <Input id="first-name" placeholder="김" required />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="last-name">이름</Label>
                <Input id="last-name" placeholder="민준" required />
              </div>
            </div>
            <div className="grid gap-2">
              <Label htmlFor="email">이메일</Label>
              <Input id="email" type="email" placeholder="m@example.com" required />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="password">비밀번호</Label>
              <Input id="password" type="password" />
            </div>
            <Button type="submit" className="w-full">
              계정 생성
            </Button>
            <Button variant="outline" className="w-full bg-transparent">
              GitHub로 가입하기
            </Button>
          </div>
          <div className="mt-4 text-center text-sm">
            이미 계정이 있으신가요?{" "}
            <Link href="/login" className="underline">
              로그인
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}

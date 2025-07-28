"use client"

import React, { useState, useRef } from "react"
import { Folder, File, Upload, FolderPlus, MoreVertical, Download, Trash2, Edit } from "lucide-react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"

type Item = {
  type: "folder" | "file"
  name: string
  size?: string
  modified: string
}

const initialItems: Item[] = [
  { type: "folder", name: "Documents", modified: "2024-07-20" },
  { type: "folder", name: "Images", modified: "2024-07-21" },
  { type: "file", name: "annual-report.pdf", size: "1.2 MB", modified: "2024-07-22" },
  { type: "file", name: "project-plan.docx", size: "512 KB", modified: "2024-07-23" },
]

export default function FileBrowser() {
  const [items, setItems] = useState<Item[]>(initialItems)
  const [path, setPath] = useState<string[]>(["My Files"])
  const [isNewFolderDialogOpen, setIsNewFolderDialogOpen] = useState(false)
  const [newFolderName, setNewFolderName] = useState("")
  const fileInputRef = useRef<HTMLInputElement>(null)

  const handleUploadClick = () => {
    fileInputRef.current?.click()
  }

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files
    if (files) {
      const newFiles: Item[] = Array.from(files).map((file) => ({
        type: "file",
        name: file.name,
        size: `${(file.size / 1024 / 1024).toFixed(2)} MB`,
        modified: new Date().toISOString().split("T")[0],
      }))
      setItems((prev) => [...prev, ...newFiles])
    }
  }

  const handleCreateFolder = () => {
    if (newFolderName.trim()) {
      const newFolder: Item = {
        type: "folder",
        name: newFolderName.trim(),
        modified: new Date().toISOString().split("T")[0],
      }
      setItems((prev) => [...prev, newFolder])
      setIsNewFolderDialogOpen(false)
      setNewFolderName("")
    }
  }

  const handleDownload = (itemName: string) => {
    // This is a simulated download
    const content = `This is a dummy file for ${itemName}.`
    const blob = new Blob([content], { type: "text/plain" })
    const url = URL.createObjectURL(blob)
    const a = document.createElement("a")
    a.href = url
    a.download = itemName
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    URL.revokeObjectURL(url)
  }

  const handleDelete = (itemName: string) => {
    setItems(items.filter((item) => item.name !== itemName))
  }

  const handleNavigate = (index: number) => {
    setPath(path.slice(0, index + 1))
    // In a real app, you would fetch the content of this directory
    // For now, we just reset to the initial items for demonstration
    if (index < path.length - 1) {
      setItems(initialItems)
    }
  }

  const handleFolderClick = (folderName: string) => {
    setPath([...path, folderName])
    // In a real app, you would fetch the content of this directory
    // For now, we just show an empty directory for demonstration
    setItems([])
  }

  return (
    <>
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle>
              <Breadcrumb>
                <BreadcrumbList>
                  {path.map((p, index) => (
                    <React.Fragment key={p}>
                      <BreadcrumbItem>
                        <BreadcrumbLink href="#" onClick={() => handleNavigate(index)}>
                          {p}
                        </BreadcrumbLink>
                      </BreadcrumbItem>
                      {index < path.length - 1 && <BreadcrumbSeparator />}
                    </React.Fragment>
                  ))}
                </BreadcrumbList>
              </Breadcrumb>
            </CardTitle>
          </div>
          <div className="flex gap-2">
            <Button variant="outline" onClick={() => setIsNewFolderDialogOpen(true)}>
              <FolderPlus className="mr-2 h-4 w-4" />
              폴더 생성
            </Button>
            <Button onClick={handleUploadClick}>
              <Upload className="mr-2 h-4 w-4" />
              파일 업로드
            </Button>
            <input type="file" ref={fileInputRef} onChange={handleFileChange} className="hidden" multiple />
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[50px]"></TableHead>
                <TableHead>이름</TableHead>
                <TableHead>수정일</TableHead>
                <TableHead>파일 크기</TableHead>
                <TableHead className="w-[50px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {items.length > 0 ? (
                items.map((item) => (
                  <TableRow
                    key={item.name}
                    className="cursor-pointer"
                    onDoubleClick={item.type === "folder" ? () => handleFolderClick(item.name) : undefined}
                  >
                    <TableCell>
                      {item.type === "folder" ? (
                        <Folder className="h-5 w-5 text-blue-500" />
                      ) : (
                        <File className="h-5 w-5 text-gray-500" />
                      )}
                    </TableCell>
                    <TableCell className="font-medium">{item.name}</TableCell>
                    <TableCell>{item.modified}</TableCell>
                    <TableCell>{item.size || "—"}</TableCell>
                    <TableCell>
                      <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                          <Button variant="ghost" size="icon">
                            <MoreVertical className="h-4 w-4" />
                          </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent>
                          {item.type === "file" && (
                            <DropdownMenuItem onClick={() => handleDownload(item.name)}>
                              <Download className="mr-2 h-4 w-4" />
                              다운로드
                            </DropdownMenuItem>
                          )}
                          <DropdownMenuItem>
                            <Edit className="mr-2 h-4 w-4" />
                            이름 변경
                          </DropdownMenuItem>
                          <DropdownMenuItem className="text-red-500" onClick={() => handleDelete(item.name)}>
                            <Trash2 className="mr-2 h-4 w-4" />
                            삭제
                          </DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={5} className="h-24 text-center">
                    폴더가 비어있습니다.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Dialog open={isNewFolderDialogOpen} onOpenChange={setIsNewFolderDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>새 폴더 생성</DialogTitle>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="folder-name" className="text-right">
                폴더명
              </Label>
              <Input
                id="folder-name"
                value={newFolderName}
                onChange={(e) => setNewFolderName(e.target.value)}
                className="col-span-3"
                placeholder="예: Project Files"
              />
            </div>
          </div>
          <DialogFooter>
            <Button type="submit" onClick={handleCreateFolder}>
              생성
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </>
  )
}

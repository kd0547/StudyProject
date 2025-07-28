"use client"

import type React from "react"
import { useState, useRef, useEffect } from "react"
import "./App.css"
import { marked } from "marked";

interface Message {
  id: string
  role: "user" | "assistant"
  content: string
}

function App() {
  const [messages, setMessages] = useState<Message[]>([])
  const [input, setInput] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const messagesEndRef = useRef<HTMLDivElement>(null)

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(() => {
    scrollToBottom()
  }, [messages])

  const sendMessage = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!input.trim() || isLoading) return

    const userMessage: Message = {
      id: Date.now().toString(),
      role: "user",
      content: input,
    }

    setMessages((prev) => [...prev, userMessage])
    setInput("")
    setIsLoading(true)

    try {
      const response = await fetch("http://localhost:8000/api/chat", {
        method: "POST",
        body: JSON.stringify({
          messages: [
            ...messages.map((msg) => ({
              role: msg.role,
              content: msg.content,
            })),
            {
              role: "user",
              content: input,
            },
          ],
        }),
      })

      if (!response.ok) {
        throw new Error("API 요청 실패")
      }


      // @ts-ignore
      const reader = response.body.getReader();
      const decoder = new TextDecoder("utf-8");

      let buffer = "";
      let currentContent = "";

      const currentMessage: Message = {
        id: Date.now().toString(),
        role: "assistant",
        content: "",
      };


      setMessages((prev) => [...prev, currentMessage]);


      while (true) {

        const {done, value} = await reader.read();
        if (done) break;

        const chunk = decoder.decode(value, {stream: true});
        buffer += chunk;

        const chunks = buffer.split("\n\n");
        buffer = chunks.pop() || "";  // 아직 덜 온 마지막 줄은 남겨두기

        for (const chunk of chunks) {
          if (!chunk.startsWith("data:")) continue;

          const jsonStr = chunk.replace("data:", "").trim();
          try {
            const data = JSON.parse(jsonStr);


            currentMessage.content += data.content;

            setMessages((prev) => [
              ...prev.slice(0, -1),
              {
                ...prev[prev.length - 1],
                content: currentMessage.content,
              },
            ]);
            if(isLoading) {
              setIsLoading(false)
            }


          } catch (e) {
            console.error("JSON parse error:", e, jsonStr);
          } 
        }
      }

    } catch (error) {
      console.error("Error:", error)
      const errorMessage: Message = {
        id: (Date.now() + 1).toString(),
        role: "assistant",
        content: "죄송합니다. 오류가 발생했습니다. 다시 시도해주세요.",
      }
      setMessages((prev) => [...prev, errorMessage])
    } finally {
      setIsLoading(false)
    }
  }

  return (
      <div className="app">
        <div className="chat-container">
          <div className="chat-header">
            <div className="header-content">
              <div className="bot-icon">🤖</div>
              <h1>AI 채팅 어시스턴트</h1>
            </div>
          </div>

          <div className="messages-container">
            {messages.length === 0 ? (
                <div className="welcome-message">
                  <div className="welcome-icon">🤖</div>
                  <h2>안녕하세요! 무엇이든 물어보세요.</h2>
                  <p>궁금한 것이 있으면 언제든지 질문해주세요.</p>
                </div>
            ) : (
                <div className="messages">
                  {messages.map((message) => (
                      <div
                          key={message.id}
                          className={`message ${message.role === "user" ? "user-message" : "assistant-message"}`}
                      >
                        <div className="message-avatar">{message.role === "user" ? "👤" : "🤖"}</div>
                        <div className="message-content">
                          <p style={{whiteSpace:"pre-wrap"}}>{message.content}</p>
                        </div>
                      </div>
                  ))}

                  {isLoading && (
                      <div className="message assistant-message">
                        <div className="message-avatar">🤖</div>
                        <div className="message-content">
                          <div className="typing-indicator">
                            <span></span>
                            <span></span>
                            <span></span>
                          </div>
                        </div>
                      </div>
                  )}
                  <div ref={messagesEndRef} />
                </div>
            )}
          </div>

          <div className="input-container">
            <form onSubmit={sendMessage} className="input-form">
              <input
                  type="text"
                  value={input}
                  onChange={(e) => setInput(e.target.value)}
                  placeholder="질문을 입력하세요..."
                  className="message-input"
                  disabled={isLoading}
              />
              <button type="submit" disabled={isLoading || !input.trim()} className="send-button">
                전송
              </button>
            </form>
          </div>
        </div>
      </div>
  )
}

export default App

/**
 * 类型定义文件
 * 未来迁移到 TypeScript 时可使用
 */

/**
 * 聊天消息
 */
export interface ChatMessage {
  role: 'user' | 'ai'
  content: string
  timestamp?: number
}

/**
 * SSE 流回调
 */
export type StreamCallback = (chunk: string) => void

/**
 * API 响应
 */
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

/**
 * 聊天请求参数
 */
export interface ChatRequest {
  memoryId: string
  userMessage: string
}

/**
 * 聊天响应（流式）
 */
export interface ChatStreamResponse {
  // SSE 返回的纯文本数据
}

/**
 * 会话信息
 */
export interface Session {
  id: string
  createdAt: number
  messages: ChatMessage[]
}

import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

/**
 * 通过 SSE 方式获取 AI 对话的流式响应
 * @param {string} memoryId - 会话 ID
 * @param {string} userMessage - 用户输入的消息
 * @param {Function} onChunk - 接收到每个数据块时的回调函数
 * @returns {Promise<void>}
 */
export const chatWithStream = (memoryId, userMessage, onChunk) => {
  return new Promise((resolve, reject) => {
    try {
      // 确保 memoryId 是字符串类型
      const memoryIdStr = String(memoryId)
      
      // 创建 EventSource 用于 SSE
      const queryParams = new URLSearchParams({
        memoryId: memoryIdStr,
        userMessage,
      })

      const eventSource = new EventSource(
        `${API_BASE_URL}/ai/chat?${queryParams.toString()}`
      )

      // 监听消息事件
      eventSource.addEventListener('message', (event) => {
        if (event.data) {
          onChunk(event.data)
        }
      })

      // 监听错误事件
      eventSource.addEventListener('error', (error) => {
        eventSource.close()
        if (error.eventPhase === EventSource.CLOSED) {
          // 连接已关闭，流式传输完成
          resolve()
        } else {
          reject(new Error('SSE 连接错误'))
        }
      })

      // 监听流结束事件（可选）
      eventSource.addEventListener('end', () => {
        eventSource.close()
        resolve()
      })
    } catch (error) {
      reject(error)
    }
  })
}

/**
 * 创建 axios 实例
 */
const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 响应拦截器
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API 请求失败:', error)
    return Promise.reject(error)
  }
)

export default axiosInstance

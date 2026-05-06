/**
 * 模拟数据服务 - 用于本地开发测试
 * 
 * 使用说明：
 * 1. 在 chatService.js 中导入此模块
 * 2. 将 chatWithStream 替换为 chatWithStreamMock
 * 3. 开发完成后改回真实接口
 * 
 * import { chatWithStreamMock } from '@/services/mockService'
 */

// 模拟的 AI 回复库
const mockResponses = [
  {
    question: /javascript|js/i,
    reply: "JavaScript 是一种强大的脚本语言。关于 JavaScript 你想了解什么具体方面呢？\n\n• 基础语法\n• 异步编程（Promise, async/await）\n• 事件循环机制\n• 原型链和继承\n• 闭包和作用域\n\n请告诉我你最感兴趣的方向！"
  },
  {
    question: /python|py/i,
    reply: "Python 以其简洁易学的特点而受欢迎。我可以帮助你学习：\n\n• Python 基础语法\n• 面向对象编程\n• 数据处理（pandas, numpy）\n• Web 框架（Django, Flask）\n• 机器学习（scikit-learn, TensorFlow）\n\n你想深入了解哪个方面？"
  },
  {
    question: /html|css|web/i,
    reply: "Web 前端开发很有意思！我可以帮助你：\n\n• HTML 语义化和最佳实践\n• CSS 布局（Flexbox, Grid）\n• 响应式设计\n• Web 性能优化\n• 浏览器兼容性\n\n有什么具体的问题吗？"
  },
  {
    question: /react|vue|angular/i,
    reply: "前端框架选择很重要！让我们讨论：\n\n• React vs Vue vs Angular\n• 组件化思想\n• 状态管理\n• 生命周期\n• 性能优化\n\n你想了解哪个框架？"
  },
  {
    question: /面试|interview/i,
    reply: "面试准备很关键！以下是常见考点：\n\n• 算法和数据结构\n• 系统设计\n• 行为问题\n• 技术深度问题\n• 手撕代码\n\n要准备哪方面的内容？我可以模拟面试或讲解知识点。"
  },
  {
    question: /.*/,
    reply: "我理解你的问题了。这是一个很好的话题！\n\n让我为你详细解释：\n\n首先，我们需要理解核心概念...\n然后，看几个实际例子...\n最后，总结最佳实践...\n\n还有其他问题吗？"
  }
]

/**
 * 模拟 SSE 流式响应
 * @param {string} memoryId - 会话 ID
 * @param {string} userMessage - 用户消息
 * @param {Function} onChunk - 每个数据块的回调
 * @returns {Promise<void>}
 */
export const chatWithStreamMock = (memoryId, userMessage, onChunk) => {
  return new Promise((resolve) => {
    // 查找匹配的响应
    let response = mockResponses[mockResponses.length - 1].reply
    for (const item of mockResponses) {
      if (item.question.test(userMessage)) {
        response = item.reply
        break
      }
    }

    // 模拟流式传输
    const words = response.split('')
    let index = 0

    const timer = setInterval(() => {
      if (index < words.length) {
        const chunk = words[index]
        onChunk(chunk)
        index++
      } else {
        clearInterval(timer)
        resolve()
      }
    }, 30) // 每 30ms 发送一个字符
  })
}

/**
 * 获取模拟的会话 ID
 */
export const getMockSessionId = () => {
  return `MOCK-${Date.now()}`
}

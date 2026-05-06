/**
 * 生成唯一的会话 ID
 * @returns {string} 唯一的会话 ID（长格式，用于显示）
 */
export const generateSessionId = () => {
  const timestamp = Date.now()
  const randomStr = Math.random().toString(36).substring(2, 10).toUpperCase()
  return `${timestamp}-${randomStr}`
}

/**
 * 生成数字会话 ID（字符串格式）
 * @returns {string} 数字会话 ID
 */
export const generateNumericSessionId = () => {
  return Date.now().toString()
}

/**
 * 格式化日期
 * @param {Date} date - 日期对象
 * @returns {string} 格式化后的日期字符串
 */
export const formatDate = (date) => {
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hours = String(d.getHours()).padStart(2, '0')
  const minutes = String(d.getMinutes()).padStart(2, '0')
  const seconds = String(d.getSeconds()).padStart(2, '0')

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

/**
 * 复制到剪贴板
 * @param {string} text - 要复制的文本
 */
export const copyToClipboard = async (text) => {
  try {
    await navigator.clipboard.writeText(text)
    return true
  } catch (error) {
    console.error('复制到剪贴板失败:', error)
    return false
  }
}

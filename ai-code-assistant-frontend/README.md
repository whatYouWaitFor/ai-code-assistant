# AI 编程小助手 - 前端项目

一个基于 Vue3 + Vite 的 AI 编程小助手前端应用，帮助用户解答编程学习和求职面试相关的问题。

## 项目特性

- ✨ 实时聊天界面，采用 SSE（Server-Sent Events）技术实现流式响应
- 🎯 自动生成唯一的会话 ID，区分不同对话
- 💬 聊天记录展示（用户信息在右，AI 信息在左）
- ⚡ 基于 Vite 的快速开发体验
- 🎨 现代化的渐变设计风格
- 📱 响应式设计，支持多种屏幕尺寸

## 项目结构

```
ai-code-assistant-frontend/
├── index.html                 # HTML 入口
├── package.json              # 项目依赖配置
├── vite.config.js            # Vite 配置
├── .gitignore                # Git 忽略文件
├── src/
│   ├── main.js              # 应用入口
│   ├── App.vue              # 根组件
│   ├── views/
│   │   └── ChatPage.vue      # 聊天页面主组件
│   ├── services/
│   │   └── chatService.js    # API 调用服务（SSE）
│   ├── utils/
│   │   └── helpers.js        # 工具函数
│   └── assets/
│       └── styles/
│           └── main.css      # 全局样式
```

## 技术栈

- **Vue 3**: 现代化的 JavaScript 框架
- **Vite**: 下一代前端构建工具
- **Axios**: HTTP 请求库（预留）
- **EventSource**: 浏览器原生 SSE 支持

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

开发服务器将在 `http://localhost:5173` 启动，并自动打开浏览器。

### 3. 构建生产版本

```bash
npm run build
```

生成的文件将输出到 `dist/` 目录。

### 4. 预览生产构建

```bash
npm run preview
```

## 核心功能说明

### 聊天流程

1. **初始化**: 进入页面时自动生成唯一的会话 ID
2. **发送消息**: 用户在输入框输入问题并发送
3. **流式响应**: 通过 SSE 连接实时接收 AI 的流式响应
4. **消息展示**: 收到的内容实时更新到聊天记录中

### SSE 实现细节

服务层使用浏览器原生的 `EventSource` API 来处理 SSE：

```javascript
const eventSource = new EventSource(
  `${API_BASE_URL}/ai/chat?memoryId=${id}&userMessage=${msg}`
)

eventSource.addEventListener('message', (event) => {
  // 处理接收到的数据块
  onChunk(event.data)
})
```

## 后端接口要求

### Chat 接口

- **端点**: `GET /api/ai/chat`
- **参数**:
  - `memoryId` (string): 会话 ID
  - `userMessage` (string): 用户消息
- **响应**: SSE 流式响应，每个事件包含一个文本数据块

### CORS 配置

后端需要配置 CORS 以支持跨域请求：

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true);
            }
        };
    }
}
```

## 环境配置

如需修改后端 API 地址，编辑 [src/services/chatService.js](src/services/chatService.js#L3)：

```javascript
const API_BASE_URL = 'http://localhost:8080/api'
```

## 浏览器兼容性

- Chrome/Edge: 完全支持
- Firefox: 完全支持
- Safari: 完全支持
- IE: 不支持（需要 EventSource 支持）

## 常见问题

### Q: 连接超时或无法接收数据？

A: 
1. 确认后端服务已启动在 `http://localhost:8080`
2. 检查后端是否配置了 CORS
3. 检查浏览器控制台的网络选项卡查看请求详情

### Q: 如何修改聊天界面风格？

A: 编辑 [src/views/ChatPage.vue](src/views/ChatPage.vue) 中的 `<style scoped>` 部分，或修改 [src/assets/styles/main.css](src/assets/styles/main.css)。

### Q: 如何处理长时间运行的请求？

A: 目前已配置了输入框禁用机制。如需更复杂的超时处理，可在 [src/services/chatService.js](src/services/chatService.js) 中添加超时逻辑。

## 开发建议

- 使用 Vue DevTools 浏览器扩展进行调试
- 在浏览器 F12 开发者工具的 Network 标签中查看 SSE 连接
- 利用 Vite 的 HMR（Hot Module Replacement）进行快速开发迭代

## 许可证

MIT

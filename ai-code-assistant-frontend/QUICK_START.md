# 快速启动指南

## 📋 项目概览

**AI 编程小助手** 前端项目已生成完成！这是一个基于 Vue3 + Vite 的现代化聊天应用。

## 🚀 立即开始

### 1️⃣ 安装依赖

```bash
cd /Users/dgj/my-project/ai-code-assistant/ai-code-assistant-frontend
npm install
```

### 2️⃣ 启动开发服务器

```bash
npm run dev
```

浏览器会自动打开 `http://localhost:5173`

### 3️⃣ 启动后端服务

在另一个终端启动你的 Spring Boot 应用（运行在 `http://localhost:8080`）

### 4️⃣ 开始聊天

在浏览器中输入问题，应用会实时显示 AI 的流式响应！

## 📁 项目结构

```
ai-code-assistant-frontend/
├── index.html                 # HTML 入口
├── vite.config.js            # Vite 配置
├── package.json              # 依赖配置
├── README.md                 # 项目文档
├── .env.example              # 环境变量示例
│
├── src/
│   ├── main.js              # 应用入口
│   ├── App.vue              # 根组件
│   ├── views/
│   │   └── ChatPage.vue      # ⭐ 聊天页面（核心组件）
│   ├── services/
│   │   └── chatService.js    # ⭐ API 服务（SSE 实现）
│   ├── utils/
│   │   └── helpers.js        # 工具函数
│   └── assets/
│       └── styles/
│           └── main.css      # 全局样式
│
└── docs/
    ├── DEVELOPMENT.md        # 开发流程指南
    ├── SSE_GUIDE.md         # SSE 技术详解
    ├── AiController.java    # 后端接口示例
    ├── CorsConfig.java      # CORS 配置示例
    └── SseConfig.java       # SSE 配置示例
```

## ✨ 核心特性

### 🎯 实时聊天
- 使用 SSE (Server-Sent Events) 实现流式响应
- 消息实时显示，无需等待完整响应

### 🔑 会话管理
- 自动生成唯一的会话 ID
- 支持多个独立的对话上下文

### 💅 现代化 UI
- 渐变设计风格
- 响应式布局
- 流畅的动画效果

### ⚙️ 开箱即用
- Vite 快速开发环境
- Vue 3 Composition API
- 模块化项目结构

## 🔗 后端集成

### 接口要求

你的后端需要提供以下接口：

```
GET /api/ai/chat?memoryId=<sessionId>&userMessage=<message>
```

返回类型：`Flux<String>` (SSE 流)

### 必要的后端配置

1. **CORS 配置** - 允许跨域请求
2. **SSE 支持** - 返回 `text/event-stream` 媒体类型

查看 `docs/` 目录中的 Java 配置示例：
- [CorsConfig.java](docs/CorsConfig.java)
- [SseConfig.java](docs/SseConfig.java)
- [AiController.java](docs/AiController.java)

## 📚 重要文档

| 文档 | 说明 |
|-----|------|
| [README.md](README.md) | 项目详细说明 |
| [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md) | 开发工作流和调试技巧 |
| [docs/SSE_GUIDE.md](docs/SSE_GUIDE.md) | SSE 技术实现详解 |

## 🛠 npm 命令

```bash
npm run dev      # 启动开发服务器
npm run build    # 构建生产版本
npm run preview  # 预览生产构建
```

## 🐛 常见问题

### Q: 无法连接到后端？
A: 
1. 确保后端已启动在 `http://localhost:8080`
2. 检查后端是否配置了 CORS
3. 查看浏览器 Network 选项卡

### Q: 消息没有实时显示？
A: 检查浏览器是否支持 SSE（Chrome, Firefox, Safari, Edge 都支持）

### Q: 如何修改 API 地址？
A: 编辑 `src/services/chatService.js` 中的 `API_BASE_URL`

### Q: 如何自定义样式？
A: 编辑 `src/views/ChatPage.vue` 或 `src/assets/styles/main.css`

## 🎓 技术栈

- **框架**: Vue 3 + Composition API
- **构建**: Vite
- **HTTP**: Axios（预装）
- **实时通信**: SSE (浏览器原生)
- **样式**: CSS 3 + 渐变

## 📞 支持

遇到问题？查看以下资源：

- [Vue 3 官方文档](https://v3.vuejs.org/)
- [Vite 官方文档](https://vitejs.dev/)
- [MDN SSE 指南](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events)
- [Spring Boot Flux 文档](https://spring.io/projects/spring-framework)

## ✅ 下一步

1. ✨ 安装依赖：`npm install`
2. 🚀 启动开发服务器：`npm run dev`
3. 📝 集成后端接口
4. 🎨 自定义样式（可选）
5. 📦 构建部署：`npm run build`

---

**项目已完全生成！现在可以开始开发了！** 🎉

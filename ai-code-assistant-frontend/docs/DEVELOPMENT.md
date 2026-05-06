# 开发流程指南

## 项目启动

### 第一次启动

1. **安装依赖**
   ```bash
   npm install
   ```

2. **启动开发服务器**
   ```bash
   npm run dev
   ```
   
   开发服务器会在 `http://localhost:5173` 启动

3. **启动后端服务**
   
   在另一个终端启动 Spring Boot 应用（应运行在 `http://localhost:8080`）

4. **打开浏览器**
   
   浏览器会自动打开 `http://localhost:5173`

## 开发工作流

### 编辑代码

由于 Vite 的 HMR（Hot Module Replacement）机制，编辑代码后会自动热更新：

- 编辑 `.vue` 文件：组件会自动更新，状态保留
- 编辑 `.js/.css` 文件：相应模块会重新加载
- 编辑 `index.html`：页面会刷新

### 调试技巧

1. **使用浏览器开发者工具**
   - F12 打开
   - Console 标签查看日志和错误
   - Network 标签查看网络请求
   - Application 标签查看本地存储

2. **使用 Vue DevTools**
   - 安装 Vue.js devtools 浏览器扩展
   - 查看组件树和状态

3. **调试 SSE 连接**
   - 在 Chrome DevTools → Network 中，选中 SSE 请求
   - 在 Messages 标签查看流式消息

### 常见编辑任务

#### 修改聊天界面样式

编辑 [src/views/ChatPage.vue](../src/views/ChatPage.vue) 中的 `<style scoped>` 部分

#### 修改全局样式

编辑 [src/assets/styles/main.css](../src/assets/styles/main.css)

#### 添加新的工具函数

在 [src/utils/helpers.js](../src/utils/helpers.js) 中添加

#### 修改 API 地址

在 [src/services/chatService.js](../src/services/chatService.js) 中修改 `API_BASE_URL`

## 构建和部署

### 开发构建

```bash
npm run build
```

输出目录：`dist/`

### 预览构建结果

```bash
npm run preview
```

在 `http://localhost:5173` 预览生产构建

### 部署注意事项

1. **后端 CORS 配置**
   
   将前端地址添加到后端的 CORS 允许列表：
   
   ```java
   .allowedOrigins("http://yourdomain.com")
   ```

2. **API 地址配置**
   
   修改 `src/services/chatService.js` 中的 `API_BASE_URL` 为生产环境地址

3. **使用环境变量**（推荐）
   
   创建 `.env.production` 文件：
   
   ```
   VITE_API_BASE_URL=https://api.yourdomain.com
   ```
   
   在 `chatService.js` 中使用：
   
   ```javascript
   const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'
   ```

## 项目配置

### 修改应用端口

在 [vite.config.js](../vite.config.js) 中修改 `server.port`

### 修改后端 API 代理

在 [vite.config.js](../vite.config.js) 中修改 `server.proxy` 的 target

### 添加新的 npm 脚本

在 [package.json](../package.json) 的 `scripts` 中添加

## 性能优化建议

1. **路由懒加载**（如果添加多页面）
   
   ```javascript
   const ChatPage = () => import('./views/ChatPage.vue')
   ```

2. **组件懒加载**
   
   ```javascript
   const MyComponent = defineAsyncComponent(
     () => import('./components/MyComponent.vue')
   )
   ```

3. **使用 Composition API**（已使用）
   
   避免过度的响应式对象创建

4. **优化 SSE 消息处理**
   
   避免在每个消息块都重新渲染整个列表

## 故障排查

### 端口被占用

```bash
# macOS/Linux 查找占用 5173 端口的进程
lsof -i :5173

# 杀死进程（替换 PID）
kill -9 <PID>
```

### 模块解析错误

```bash
# 清除缓存后重新启动
rm -rf node_modules .vite dist
npm install
npm run dev
```

### 热更新失效

- 检查文件是否被保存
- 尝试手动刷新浏览器
- 重启开发服务器

### SSE 连接问题

- 确保后端服务已启动
- 检查后端 CORS 配置
- 在 Network 标签查看请求详情
- 查看浏览器 console 的错误信息

## 代码风格

### Vue 组件

使用 Composition API + `<script setup>` 语法：

```vue
<script setup>
import { ref } from 'vue'

const count = ref(0)
const increment = () => count.value++
</script>
```

### JavaScript

- 使用 ES6+ 语法
- 使用 const/let，避免 var
- 使用箭头函数
- 添加必要的注释

### 命名规范

- 文件名：PascalCase（组件）、camelCase（工具）
- 变量名：camelCase
- 常量：UPPER_SNAKE_CASE
- 类名/组件名：PascalCase

## 提交规范

建议使用如下的提交信息格式：

```
feat: 添加新功能
fix: 修复 bug
docs: 更新文档
style: 代码格式调整
refactor: 代码重构
test: 添加/修改测试
chore: 构建/依赖等变更
```

## 进阶开发

### 添加 TypeScript

1. 安装依赖
   ```bash
   npm install -D typescript vue-tsc
   ```

2. 创建 `tsconfig.json`

3. 将 `.js` 改为 `.ts`，`.vue` 添加 `lang="ts"`

### 添加 ESLint

```bash
npm install -D eslint eslint-plugin-vue
```

### 添加单元测试

```bash
npm install -D vitest @testing-library/vue
```

## 参考资源

- [Vue 3 文档](https://v3.vuejs.org/)
- [Vite 文档](https://vitejs.dev/)
- [Axios 文档](https://axios-http.com/)
- [MDN SSE 指南](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events)

# ✅ 项目初始化检查清单

## 🔍 安装前检查

- [ ] Node.js 版本 >= 14.0（推荐 16.0+）
- [ ] npm 版本 >= 6.0（或使用 yarn/pnpm）
- [ ] Git 已安装（可选，但推荐）

验证 Node.js 版本：
```bash
node --version
npm --version
```

---

## 📦 项目初始化步骤

### 第 1 步：安装依赖

```bash
# 进入项目目录
cd ai-code-assistant-frontend

# 安装依赖
npm install
```

**预期输出**：
- ✅ node_modules 文件夹被创建
- ✅ package-lock.json 被生成

### 第 2 步：启动开发服务器

```bash
npm run dev
```

**预期输出**：
```
VITE v4.x.x  ready in xxx ms

➜  Local:   http://localhost:5173/
➜  press h to show help
```

**浏览器会自动打开** `http://localhost:5173/`

### 第 3 步：检查页面

在浏览器中，你应该看到：
- [x] "AI 编程小助手" 标题
- [x] 会话 ID 显示（格式：timestamp-randomStr）
- [x] 聊天记录区域（空的）
- [x] 输入框和发送按钮

---

## 🚀 后端集成步骤

### 1️⃣ 后端配置

#### 方式一：使用提供的配置类

复制以下文件到你的 Spring Boot 项目：
- `docs/CorsConfig.java` → 你的 `config/` 包
- `docs/SseConfig.java` → 你的 `config/` 包（可选）

#### 方式二：手动配置

在你的 Spring Boot 配置类中添加 CORS：

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET", "POST", "OPTIONS");
    }
}
```

### 2️⃣ 实现 Chat 接口

```java
@RestController
@RequestMapping("/api/ai")
public class AiController {
    
    @GetMapping("/chat")
    public Flux<String> chat(
            @RequestParam int memoryId,
            @RequestParam String userMessage) {
        
        // 你的 AI 服务实现
        return aiService.chatWithStream(memoryId, userMessage);
    }
}
```

### 3️⃣ 启动后端

```bash
# 在另一个终端启动 Spring Boot 应用
mvn spring-boot:run
```

**后端应该运行在** `http://localhost:8080`

### 4️⃣ 测试连接

1. 打开前端应用 `http://localhost:5173/`
2. 在输入框输入任何问题
3. 点击发送

**预期结果**：
- 消息出现在右边（用户消息）
- AI 消息实时出现在左边
- 没有 CORS 错误

---

## 🧪 本地测试（无需后端）

### 使用模拟数据进行测试

编辑 `src/views/ChatPage.vue`，修改导入：

```javascript
// 原来的导入
import { chatWithStream } from '@/services/chatService'

// 改为
import { chatWithStreamMock as chatWithStream } from '@/services/mockService'
```

然后测试：
1. 输入问题（包含关键词：javascript, python, html, react, 面试等）
2. 应该看到相关的 AI 回复

**完成后改回原来的导入！**

---

## 📋 项目文件检查

### 源代码文件

- [ ] `src/main.js` 存在
- [ ] `src/App.vue` 存在
- [ ] `src/views/ChatPage.vue` 存在
- [ ] `src/services/chatService.js` 存在
- [ ] `src/utils/helpers.js` 存在
- [ ] `src/assets/styles/main.css` 存在

### 配置文件

- [ ] `package.json` 存在
- [ ] `vite.config.js` 存在
- [ ] `index.html` 存在
- [ ] `.gitignore` 存在

### 文档文件

- [ ] `README.md` 存在
- [ ] `QUICK_START.md` 存在
- [ ] `docs/DEVELOPMENT.md` 存在
- [ ] `docs/SSE_GUIDE.md` 存在

---

## 🐛 常见问题排查

### ❌ 问题：npm install 失败

**解决方案**：
```bash
# 清除缓存
npm cache clean --force

# 删除 node_modules
rm -rf node_modules

# 重新安装
npm install
```

### ❌ 问题：端口 5173 已被占用

**解决方案**：
```bash
# 查找占用进程（macOS/Linux）
lsof -i :5173

# 杀死进程
kill -9 <PID>

# 或修改端口（vite.config.js）
server: { port: 5174 }
```

### ❌ 问题：连接到后端失败

**检查清单**：
- [ ] 后端已启动（`http://localhost:8080`）
- [ ] 后端配置了 CORS
- [ ] 防火墙没有阻止
- [ ] 查看浏览器 DevTools → Network → 错误详情

### ❌ 问题：消息没有实时显示

**检查清单**：
- [ ] 浏览器支持 SSE（Chrome, Firefox, Safari, Edge）
- [ ] 后端正确返回 `Flux<String>`
- [ ] 查看 Network → EventStream 是否接收数据
- [ ] 查看 Console 是否有 JavaScript 错误

### ❌ 问题：页面加载很慢

**解决方案**：
```bash
# 确保在开发模式下运行（不是生产构建）
npm run dev

# 而不是
npm run build && npm run preview
```

---

## 🎨 首次自定义

### 修改颜色主题

编辑 `src/views/ChatPage.vue` 中的 `<style scoped>`：

```css
/* 改变渐变颜色 */
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

/* 改为你喜欢的颜色，例如 */
background: linear-gradient(135deg, #FF6B6B 0%, #4ECDC4 100%);
```

### 修改应用标题

编辑 `index.html`：
```html
<title>AI 编程小助手</title>
<!-- 改为 -->
<title>你的应用名称</title>
```

### 修改默认问候消息

添加默认消息到 `src/views/ChatPage.vue`：
```javascript
onMounted(() => {
  sessionId.value = generateSessionId()
  
  // 添加欢迎消息
  messages.value.push({
    role: 'ai',
    content: '👋 欢迎使用 AI 编程小助手！请输入你的问题...'
  })
})
```

---

## 📦 生产构建

### 构建应用

```bash
npm run build
```

**预期输出**：
- ✅ `dist/` 文件夹被创建
- ✅ 包含 HTML、JS、CSS 等文件

### 预览构建结果

```bash
npm run preview
```

**在生产模式下测试应用**

### 部署到服务器

1. 确保 `dist/` 文件夹已生成
2. 将 `dist/` 文件夹内容上传到服务器
3. 配置后端 CORS 允许新的域名
4. 修改 `src/services/chatService.js` 中的 `API_BASE_URL` 为生产环境地址

---

## 🚢 部署检查清单

部署前，请检查：

- [ ] 生产构建成功（`npm run build` 无错误）
- [ ] 后端 API 已部署
- [ ] CORS 配置已更新为新域名
- [ ] API_BASE_URL 已更新
- [ ] 环境变量已配置
- [ ] 测试了所有功能

---

## 📞 获取帮助

遇到问题？按照以下步骤：

1. **查看文档**
   - [QUICK_START.md](QUICK_START.md) - 快速开始
   - [README.md](README.md) - 详细说明
   - [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md) - 开发指南

2. **查看浏览器控制台**
   - F12 打开 DevTools
   - Console 标签查看错误
   - Network 标签查看请求

3. **测试后端连接**
   ```bash
   curl "http://localhost:8080/api/ai/chat?memoryId=1&userMessage=test"
   ```

4. **启用模拟服务**
   - 临时改用 `mockService.js` 测试前端

---

## ✅ 初始化完成检查

一切就绪后，你应该能够：

- [x] 启动开发服务器（`npm run dev`）
- [x] 在浏览器中看到聊天页面
- [x] 输入问题并看到 AI 回复
- [x] 构建生产版本（`npm run build`）
- [x] 预览生产构建（`npm run preview`）

**恭喜！项目已完全就绪！** 🎉

---

*Last Updated: 2026-05-06*

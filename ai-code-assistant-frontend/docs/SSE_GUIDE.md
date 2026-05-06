# SSE (Server-Sent Events) 实现指南

## 概述

本项目使用 SSE 技术实现 AI 对话的实时流式响应，提供更好的用户体验。

## 什么是 SSE？

SSE（Server-Sent Events）是一种标准的 Web API，允许服务器通过单向 HTTP 连接主动向客户端推送事件。相比于 WebSocket，SSE 更轻量级，基于 HTTP 协议，无需特殊的网络配置。

## 前端实现

### 1. 建立 SSE 连接

```javascript
const eventSource = new EventSource(
  `http://localhost:8080/api/ai/chat?memoryId=${id}&userMessage=${msg}`
)
```

### 2. 监听消息事件

```javascript
eventSource.addEventListener('message', (event) => {
  console.log('Received data:', event.data)
  // 更新 UI
})
```

### 3. 错误处理

```javascript
eventSource.addEventListener('error', (error) => {
  if (error.eventPhase === EventSource.CLOSED) {
    console.log('Connection closed')
  } else {
    console.error('Connection error')
  }
  eventSource.close()
})
```

### 4. 关闭连接

```javascript
eventSource.close()
```

## 后端实现（Spring Boot + Project Reactor）

### 1. 依赖配置

```xml
<!-- 用于 Flux 响应 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

### 2. 控制器实现

```java
@GetMapping("/chat")
public Flux<String> chat(
        @RequestParam int memoryId,
        @RequestParam String userMessage) {
    
    return aiService.chatWithStream(memoryId, userMessage);
}
```

### 3. 服务实现示例

```java
@Service
public class AiService {
    
    public Flux<String> chatWithStream(int memoryId, String userMessage) {
        return Flux.create(sink -> {
            // 模拟流式数据
            String[] words = {"Hello", " ", "from", " ", "AI"};
            
            for (String word : words) {
                sink.next(word);
                try {
                    Thread.sleep(100); // 延迟模拟
                } catch (InterruptedException e) {
                    sink.error(e);
                    return;
                }
            }
            
            sink.complete();
        })
        .doOnCancel(() -> System.out.println("Stream cancelled"));
    }
}
```

## 关键注意事项

### 1. 响应类型

必须返回 `Flux<String>` 而不是 `ResponseEntity<Flux<String>>`：

```java
// ❌ 错误
return ResponseEntity.ok(flux);

// ✅ 正确
return flux;
```

### 2. 媒体类型

确保返回正确的 Content-Type：

```java
@GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> chat(...) {
    // ...
}
```

### 3. CORS 配置

必须配置 CORS 以支持跨域 SSE：

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
                    .allowedMethods("GET", "POST", "OPTIONS")
                    .allowCredentials(true);
            }
        };
    }
}
```

### 4. 连接保活

SSE 连接默认会超时，可以定期发送心跳：

```java
public Flux<String> chat(...) {
    return Flux.interval(Duration.ofSeconds(1))
        .map(seq -> "data: heartbeat\n\n")
        .mergeWith(actualDataFlux);
}
```

## 浏览器兼容性

- ✅ Chrome 6+
- ✅ Firefox 6+
- ✅ Safari 5.1+
- ✅ Edge 14+
- ❌ Internet Explorer（完全不支持）

## 调试技巧

### 1. 在浏览器开发者工具中查看

打开 F12 → Network，可以看到 SSE 连接：
- Type: fetch
- Protocol: http/1.1
- Status: 200
- Response: 流式数据

### 2. 记录连接状态

```javascript
eventSource.onopen = () => console.log('Connected')
eventSource.onerror = () => console.log('Connection failed')
```

### 3. 使用 curl 测试

```bash
curl -X GET "http://localhost:8080/api/ai/chat?memoryId=1&userMessage=test"
```

## 常见问题排查

### 问题 1: 连接立即关闭

**原因**: 后端没有返回 Flux 或响应了错误

**解决**: 检查日志，确保服务返回正确的 Flux

### 问题 2: 浏览器收不到数据

**原因**: 
1. CORS 未配置
2. Content-Type 错误
3. 后端没有正确写入数据

**解决**: 检查 Network 选项卡的响应头和 console 的 CORS 错误

### 问题 3: 连接超时

**原因**: 后端处理时间过长，浏览器超时

**解决**: 
1. 在处理期间定期发送数据
2. 增加超时配置
3. 显示进度指示器

## 性能优化

1. **数据分块**: 不要一次性发送所有数据
2. **背压处理**: 使用 Flux 的背压机制
3. **连接复用**: 尽量减少建立新连接
4. **内存管理**: 及时关闭不需要的连接

## 参考资源

- [MDN - Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events)
- [Spring Flux Documentation](https://spring.io/projects/spring-framework)
- [EventSource API Spec](https://html.spec.whatwg.org/multipage/server-sent-events.html)

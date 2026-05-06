package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;
import java.time.Duration;

/**
 * AI 编程小助手 API 接口
 * 提供聊天功能的流式响应
 */
@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiCodeAssistantService aiCodeAssistantService;

    /**
     * 聊天接口 - SSE 流式响应
     *
     * @param memoryId 会话 ID，用于标识不同的对话上下文
     * @param userMessage 用户输入的消息
     * @return Flux<String> 流式响应，实时返回 AI 的对话内容
     * 
     * 前端调用示例：
     * const eventSource = new EventSource(
     *   'http://localhost:8080/api/ai/chat?memoryId=SESSION_ID&userMessage=YOUR_MESSAGE'
     * )
     * 
     * eventSource.addEventListener('message', (event) => {
     *   console.log('Received:', event.data)
     * })
     * 
     * eventSource.addEventListener('error', (error) => {
     *   eventSource.close()
     * })
     */
    @GetMapping("/chat")
    public Flux<String> chat(
            @RequestParam String memoryId,
            @RequestParam String userMessage) {
        
        // 调用服务返回流式响应
        // 如果需要整数类型，可在此转换：int id = Integer.parseInt(memoryId);
        return aiCodeAssistantService.chatWithStream(memoryId, userMessage);
    }
}

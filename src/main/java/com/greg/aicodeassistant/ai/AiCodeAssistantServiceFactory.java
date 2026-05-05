package com.greg.aicodeassistant.ai;

import com.greg.aicodeassistant.ai.guardrail.SafeInputGuardrail;
import com.greg.aicodeassistant.ai.tools.MyMathTool;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeAssistantServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Resource
    private StreamingChatModel qwenStreamingChatModel;

    //@Resource
    //private ChatModel myQwenChatModel;

    @Resource
    private ContentRetriever contentRetriever;

    //@Resource
    private McpToolProvider mcpToolProvider;

    @Bean
    public AiCodeAssistantService create() {
        AiCodeAssistantService aiCodeAssistantService = AiServices.builder(AiCodeAssistantService.class)
                .streamingChatModel(qwenStreamingChatModel)
                //.chatModel(qwenChatModel)
                // 绑定请求守护
                //.inputGuardrails(new SafeInputGuardrail())
                // 每个会话独立存储，最多保持10条消息
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                // RAG检索增强生成
                .contentRetriever(contentRetriever)
                // 绑定工具
                .tools(new MyMathTool())
                // mcp工具调用
                //.toolProvider(mcpToolProvider)
                .build();
        return aiCodeAssistantService;
    }
}


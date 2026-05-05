package com.greg.aicodeassistant.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeAssistant {

    @Resource
    private ChatModel qwenChatModel;

    private final String SYSTEM_MESSAGE = """
            你是编程领域的小助手，帮助用户解答编程学习和求职面试相关的问题，并给出建议。重点关注 4 个方向：
            1. 规划清晰的编程学习路线
            2. 提供项目学习建议
            3. 给出程序员求职全流程指南（比如简历优化、投递技巧）
            4. 分享高频面试题和面试技巧
            请用简洁易懂的语言回答，助力用户高效学习与求职。
            """;

    public String chat(String message) {
        UserMessage userMessage = UserMessage.from(message);
        ChatResponse chat = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chat.aiMessage();
        log.info("aiMessage: {}", aiMessage.toString());
        return aiMessage.text();
    }

    /**
     * 多模态模型 ，千问的要换成这个qwen-vl-plus
     * @param userMessage
     * @return
     */
    public String chatWithMessage(UserMessage  userMessage) {
        ChatResponse chat = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chat.aiMessage();
        log.info("aiMessage: {}", aiMessage.toString());
        return aiMessage.text();
    }

    public String chatWithSystemMessage(String message) {
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        UserMessage userMessage = UserMessage.from(message);
        ChatResponse chat = qwenChatModel.chat(userMessage, systemMessage);
        AiMessage aiMessage = chat.aiMessage();
        log.info("aiMessage: {}", aiMessage.toString());
        return aiMessage.text();
    }
}

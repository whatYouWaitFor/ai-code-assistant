package com.greg.aicodeassistant;

import com.greg.aicodeassistant.ai.AiCodeAssistant;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeAssistantApplicationTests {

    @Resource
    private AiCodeAssistant aiCodeAssistant;
    @Test
    void contextLoads() {
    }

    @Test
    void chat() {
        aiCodeAssistant.chat("你好，我是佛山彭于晏");
    }

    @Test
    void chatWithMessage() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("描述图片"),
                ImageContent.from("https://tse2-mm.cn.bing.net/th/id/OIP-C.l7StzaQZuDHBabhKy1WQEgHaHY?w=167&h=180&c=7&r=0&o=7&dpr=1.5&pid=1.7&rm=3")
        );
        aiCodeAssistant.chatWithMessage(userMessage);
    }

    @Test
    void chatWithSystemMessage() {
        aiCodeAssistant.chatWithSystemMessage("你好，我是佛山彭于晏");
    }
}

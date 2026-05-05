package com.greg.aicodeassistant.ai;

import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeAssistantServiceTest {

    @Resource
    private AiCodeAssistantService aiCodeAssistantService;

    @Test
    void chat() {
        String chat = aiCodeAssistantService.chat("你好，我是佛山彭于晏");
        System.out.println( chat);
        chat = aiCodeAssistantService.chat("我是谁");
        System.out.println( chat);
    }

    @Test
    void chatWithMemory() {
        System.out.println(aiCodeAssistantService.chatWithMemory(1, "你好，我是佛山彭于晏"));
        //System.out.println(aiCodeAssistantService.chatWithMemory(2, "你好，我是大帅哥"));
        System.out.println(aiCodeAssistantService.chatWithMemory(1, "我是谁"));
        //System.out.println(aiCodeAssistantService.chatWithMemory(2, "我是谁"));


        System.out.println(aiCodeAssistantService.chatWithMemory(1, "再问你一次，记得我是谁吗"));
        System.out.println(aiCodeAssistantService.chatWithMemory(1, "用我的名称，给我写首诗"));
        System.out.println("-----");
    }

    @Test
    void structOut() {
        System.out.println(aiCodeAssistantService.userInfo("你好，我是佛山彭于晏"));
    }

    @Test
    void chatForReport() {
        AiCodeAssistantService.StudyReport report = aiCodeAssistantService.chatForReport("我叫小王，学习编程两年半，帮我制定学习报告");
        System.out.println(report);
    }

    @Test
    void chatForRag() {
        Result<String> result = aiCodeAssistantService.chatWithRag("怎么学习java？给一条明路");
        System.out.println(result);
        System.out.println(result.content());
        System.out.println(result.sources());
    }

    @Test
    void chatWithTool() {
        String chat = aiCodeAssistantService.chat("What is the square root of 475695037565");
        System.out.println(chat);
    }

    @Test
    void chatWithStream() {
        Flux<String> result = aiCodeAssistantService.chatWitStream(1, "What is the square root of 475695037565");
        System.out.println(result);
    }

}
package com.greg.aicodeassistant.ai;

import com.greg.aicodeassistant.ai.guardrail.SafeInputGuardrail;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * AI Service方式
 */
@InputGuardrails({SafeInputGuardrail.class})
public interface AiCodeAssistantService {

    @InputGuardrails(SafeInputGuardrail.class)
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String message);

    /**
     * 增加会话记忆
     * @param memoryId
     * @param message
     * @return
     */
    String chatWithMemory(@MemoryId int memoryId, @UserMessage String message);

    /**
     * 指定输出json格式
     * @param text
     * @return
     */
    @SystemMessage("只返回合法JSON，不要其他内容")
    String userInfo( String text);
    //String userInfo(@V("text") String text);

    record StudyReport(String name, List<String> suggestions) {
    }

    /**
     * 生成学习报告
     * @param userMessage
     * @return
     */
    @SystemMessage("只返回合法JSON，不要其他内容")
    StudyReport chatForReport(String userMessage);

    /**
     * 使用RAG，并用Result可输出引用的源文档
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String userMessage);

    @SystemMessage(fromResource = "system-prompt.txt")
    Flux<String> chatWitStream(@MemoryId long memoryId, @UserMessage String userMessage);
}

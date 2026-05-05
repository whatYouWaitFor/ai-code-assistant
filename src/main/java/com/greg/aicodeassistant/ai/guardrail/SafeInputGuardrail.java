package com.greg.aicodeassistant.ai.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * 输入防护
 */
public class SafeInputGuardrail implements InputGuardrail {

    private static final Set<String> BLACKLIST = Set.of("fuck", "shit", "cunt", "pussy", "bitch", "asshole", "kill", "evil");
    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String inputText = userMessage.singleText().toLowerCase();
        String[] words = inputText.split("\\W+");
        for (String word : words) {
            if (BLACKLIST.contains(word)){
                return fatal("Input contains blacklisted words");
            }
        }
        return success();
    }
}

package com.greg.aicodeassistant.ai.tools;

import dev.langchain4j.agent.tool.Tool;

public class MyMathTool {

    @Tool(name = "squareRoot", value = "Return a square root of a given number")
    double squareRoot(double x) {
        return Math.sqrt(x);
    }
}

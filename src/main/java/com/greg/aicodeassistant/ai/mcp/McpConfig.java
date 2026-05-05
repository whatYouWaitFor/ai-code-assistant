package com.greg.aicodeassistant.ai.mcp;

import dev.langchain4j.mcp.McpToolProvider;

import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class McpConfig {

    @Bean
    public McpToolProvider mcpToolProvider() {
        // 和MCP建立通讯
        McpTransport transport = StreamableHttpMcpTransport.builder()
                // 输入MCP服务商提供的sse地址,使用SSE的方式调用MCP
                .url("http://localhost:3001/mcp")
                .logRequests(true) // if you want to see the traffic in the log
                .logResponses(true)
                .build();
        // 创建MCP客户端
        McpClient mcpClient = DefaultMcpClient.builder()
                .key("MyMCPClient")
                .transport(transport)
                .build();
        // 创建MCP工具提供者
        McpToolProvider mcpToolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();

        return mcpToolProvider;
    }
}

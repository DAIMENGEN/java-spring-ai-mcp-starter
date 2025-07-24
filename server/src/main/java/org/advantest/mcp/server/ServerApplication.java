package org.advantest.mcp.server;

import org.advantest.mcp.server.service.ShuttleBusService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider shuttleBusTools(ShuttleBusService shuttleBusService) {
        return MethodToolCallbackProvider.builder().toolObjects(shuttleBusService).build();
    }
}

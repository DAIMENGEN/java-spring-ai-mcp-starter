package org.advantest.mcp.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.AsyncMcpToolCallbackProvider;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    private OllamaChatModel chatModel;

    private AsyncMcpToolCallbackProvider toolCallbackProvider;

    @Autowired
    public void setChatModel(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Autowired
    public void setToolCallbackProvider(AsyncMcpToolCallbackProvider toolCallbackProvider) {
        this.toolCallbackProvider = toolCallbackProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();
        ChatClient chatClient = ChatClient.create(chatModel);
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("🧠 Welcome to Spring AI Chat CLI");
        System.out.println("Type your questions below and press [Enter].");
        System.out.println("Type \"exit\" or \"quit\" to end the session.");
        System.out.println("========================================\n");
        while (true) {
            System.out.print("💬 You: ");
            String query = scanner.nextLine().trim();
            if (query.equalsIgnoreCase("exit") || query.equalsIgnoreCase("quit")) {
                System.out.println("\n👋 Session ended. Goodbye!");
                break;
            }
            if (query.isEmpty()) {
                System.out.println("⚠️  Empty input. Please enter a valid query.");
                continue;
            }
            System.out.println("🤖 AI is thinking...\n");
            chatClient.prompt(query)
                    .toolCallbacks(toolCallbacks)
                    .stream()
                    .chatResponse() // 返回 Flux<...>
                    .doOnNext(response -> {
                        String text = response.getResult().getOutput().getText();
                        if ("\n".equals(text)) {
                            System.out.println();
                        } else {
                            System.out.print(text);
                        }
                    }).doOnComplete(System.out::println)
                    .doOnError(err -> {
                        System.err.println("\n❌ Error during chat response: " + err.getMessage());
                    })
                    .then()
                    .block();
        }
    }
}

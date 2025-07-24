# Spring AI MCP Starter Project

This project demonstrates a simple implementation of a Model Coordination Protocol (MCP) client-server architecture using Spring AI. It includes a client application that interacts with an AI model through Ollama and a server application that provides custom tools via MCP.

## Project Structure

The project consists of two main modules:

1. **client**: A command-line interface application that communicates with an AI model and utilizes tools provided by the server
2. **server**: A web service that exposes custom tools via MCP protocol

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- Ollama with qwen3:8b model installed
- Access to an MCP-compatible server (this project includes a sample server)

### Running the Applications

#### Server Application

1. Navigate to the server directory:
   ```bash
   cd server
   ```


2. Run the server:
   ```bash
   mvn spring-boot:run
   ```


The server will start on port 8080 by default.

#### Client Application

1. Navigate to the client directory:
   ```bash
   cd client
   ```


2. Run the client:
   ```bash
   mvn spring-boot:run
   ```


The client will start on port 9090 and connect to the server at http://localhost:8080.

### Configuration

#### Client Configuration ([application.yaml](file://C:\Users\menge\Desktop\workspace\mcp\java-spring-ai-mcp-starter\client\target\classes\application.yaml))

- Configured to use Ollama with the qwen3:8b model
- Connects to the server at http://localhost:8080/
- Uses asynchronous MCP client

#### Server Configuration ([application.yaml](file://C:\Users\menge\Desktop\workspace\mcp\java-spring-ai-mcp-starter\client\target\classes\application.yaml))

- Provides shuttle bus information tools
- Configured as an asynchronous MCP server
- Exposes tools, resources, prompts, and completions capabilities

### Features

#### Server Tools

The server provides shuttle bus information through two tools:

1. [getShuttleBusInfo()](file://C:\Users\menge\Desktop\workspace\mcp\java-spring-ai-mcp-starter\server\src\main\java\org\advantest\mcp\server\service\ShuttleBusService.java#L48-L53): Retrieves all current shuttle bus information
2. [getShuttleBusInfoByTime(String timeRange)](file://C:\Users\menge\Desktop\workspace\mcp\java-spring-ai-mcp-starter\server\src\main\java\org\advantest\mcp\server\service\ShuttleBusService.java#L55-L76): Retrieves shuttle bus information for a specified time range (format: HH:mm-HH:mm)

#### Client Interface

The client provides a command-line interface where users can:
- Ask questions that may utilize the server's tools
- Get real-time responses from the AI model
- Exit the session by typing "exit" or "quit"

### Dependencies

#### Client Dependencies

- `spring-ai-starter-model-ollama`: For Ollama integration
- `spring-ai-starter-mcp-client-webflux`: For MCP client functionality

#### Server Dependencies

- `spring-ai-starter-mcp-server-webflux`: For MCP server functionality

## Usage

After starting both applications:

1. The client will present a command-line interface
2. Type your questions or requests
3. The AI will process your input and may use the server's tools when needed
4. Responses will be displayed in real-time
5. Type "exit" or "quit" to end the session

## Customization

You can customize the tools by modifying the [ShuttleBusService](file://C:\Users\menge\Desktop\workspace\mcp\java-spring-ai-mcp-starter\server\src\main\java\org\advantest\mcp\server\service\ShuttleBusService.java#L14-L77) class in the server module or by adding new `@Tool` annotated methods. The client automatically discovers and uses available tools through the MCP protocol.
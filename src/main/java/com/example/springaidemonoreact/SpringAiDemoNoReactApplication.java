package com.example.springaidemonoreact;

import com.example.springaidemonoreact.component.PostgresChatMemory;
import com.example.springaidemonoreact.repository.ChatRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiDemoNoReactApplication {

  @Autowired
  private ChatRepository chatRepository;

  @Bean
  public ChatClient chatClient(ChatClient.Builder builder) {
    return builder.defaultAdvisors(getAdvisor()).build();
  }

  private Advisor getAdvisor() {
    return MessageChatMemoryAdvisor.builder(getChatMemory()).build();
  }

  private ChatMemory getChatMemory() {
    return PostgresChatMemory.builder()
        .maxMessages(2)
        .chatMemoryRepository(chatRepository)
        .build();
  }

  public static void main(String[] args) {
    var context = SpringApplication.run(SpringAiDemoNoReactApplication.class, args);
  }

}

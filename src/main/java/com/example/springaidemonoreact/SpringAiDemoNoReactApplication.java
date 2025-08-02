package com.example.springaidemonoreact;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiDemoNoReactApplication {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        var context = SpringApplication.run(SpringAiDemoNoReactApplication.class, args);
        var chatClient = context.getBean(ChatClient.class);
        System.out.println(chatClient.prompt().user("Напиши текст песни Queen Багемская рапсодия").call().content());
    }

}

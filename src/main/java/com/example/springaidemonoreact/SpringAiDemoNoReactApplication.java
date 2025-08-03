package com.example.springaidemonoreact;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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

        // печатаем ответ полсе формирования полного ответа от модели
        System.out.println(chatClient.prompt().user("Напиши текст песни Queen Багемская рапсодия").call().content());

        // выводим в режиме стриминга по мере поступления токенов
//        chatClient.prompt()
//                .user("Напиши текст песни Queen Багемская рапсодия")
//                .stream()
//                .content()
//                .doOnNext(System.out::print)
//                .blockLast();

    }

}

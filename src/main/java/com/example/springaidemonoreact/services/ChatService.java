package com.example.springaidemonoreact.services;

import com.example.springaidemonoreact.component.PostgresChatMemory;
import com.example.springaidemonoreact.model.Chat;
import com.example.springaidemonoreact.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.example.springaidemonoreact.model.Role.ASSISTANT;
import static com.example.springaidemonoreact.model.Role.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final ChatRepository chatRepository;
    private final ChatWriterService chatWriterService;
    private final PostgresChatMemory postgresChatMemory;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

    public Chat createNewChat(String title) {
        var chat = Chat.builder()
                .title(title)
                .build();
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Transactional
    public void processInteraction(Long chatId, String prompt) {
        chatWriterService.addChatEntry(chatId, prompt, USER);
        var answer = chatClient.prompt().user(prompt).call().content();
        chatWriterService.addChatEntry(chatId, answer, ASSISTANT);
    }

    public SseEmitter processInteractionStreaming(Long chatId, String userPrompt) {
        log.info("Call LLM :: Start streaming");
        chatWriterService.addChatEntry(chatId, userPrompt, USER);

        var resultCompletedAnswer = new StringBuilder();
        var sseEmitter = new SseEmitter(0L);

        chatClient
                .prompt()
                .advisors(MessageChatMemoryAdvisor.builder(postgresChatMemory).conversationId(String.valueOf(chatId)).build())
                .user(userPrompt)
                .stream()
                .chatResponse()
                .subscribe(
                        response -> processToken(response, sseEmitter, resultCompletedAnswer),
                        sseEmitter::completeWithError
                        // больше не нужно, адвизор сделает это за нас
//                        , () -> chatWriterService.addChatEntry(chatId, resultCompletedAnswer.toString(), ASSISTANT
//                        )
                );

        return sseEmitter;
    }

    @SneakyThrows
    private static void processToken(ChatResponse response, SseEmitter sseEmitter, StringBuilder resultCompletedAnswer) {
        var token = response.getResult().getOutput();
        sseEmitter.send(token);
        resultCompletedAnswer.append(token.getText());
    }
}

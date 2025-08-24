package com.example.springaidemonoreact.services;

import com.example.springaidemonoreact.model.Chat;
import com.example.springaidemonoreact.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springaidemonoreact.model.Role.ASSISTANT;
import static com.example.springaidemonoreact.model.Role.USER;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final ChatRepository chatRepository;
    private final ChatWriterService chatWriterService;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat getChat(Long uuid) {
        return chatRepository.findById(uuid).orElseThrow();
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

}

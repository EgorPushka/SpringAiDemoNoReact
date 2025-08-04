package com.example.springaidemonoreact.services;

import com.example.springaidemonoreact.model.Chat;
import com.example.springaidemonoreact.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getAllChats() {
//        return chatRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
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
}

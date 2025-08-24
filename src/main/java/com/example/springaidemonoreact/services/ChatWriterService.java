package com.example.springaidemonoreact.services;

import com.example.springaidemonoreact.model.ChatEntry;
import com.example.springaidemonoreact.model.Role;
import com.example.springaidemonoreact.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatWriterService {

    private final ChatRepository chatRepository;

    @Transactional
    public void addChatEntry(Long chatId, String promt, Role role) {
        var chat = chatRepository.findById(chatId).orElseThrow();
        chat.addEntry(ChatEntry.builder().content(promt).chat(chat).role(role).build());
    }
}

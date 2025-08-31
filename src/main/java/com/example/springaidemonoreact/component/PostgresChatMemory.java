package com.example.springaidemonoreact.component;

import com.example.springaidemonoreact.model.ChatEntry;
import com.example.springaidemonoreact.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostgresChatMemory implements ChatMemory {

    private final ChatRepository chatRepository;

    @Override
    @Transactional
    public void add(@NonNull String conversationId, List<Message> messages) {
        for (Message message : messages) {
            var chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
            chat.addEntry(ChatEntry.toChatEntry(message, chat));
        }
    }

    @Override
    public List<Message> get(@NonNull String conversationId) {
        var chat = chatRepository.findById(Long.valueOf(conversationId)).orElseThrow();
        return chat.getHistory().stream()
                .map(ChatEntry::toMessage)
                .toList();
    }

    @Override
    public void clear(@NonNull String conversationId) {
        // not implemented
    }
}

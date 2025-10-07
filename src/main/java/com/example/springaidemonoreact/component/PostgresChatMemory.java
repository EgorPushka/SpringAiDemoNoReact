package com.example.springaidemonoreact.component;

import com.example.springaidemonoreact.model.ChatEntry;
import com.example.springaidemonoreact.repository.ChatRepository;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.lang.NonNull;

@Builder
public class PostgresChatMemory implements ChatMemory {

  private ChatRepository chatMemoryRepository;

  private int maxMessages;

  @Override
  public void add(@NonNull String conversationId, List<Message> messages) {
    var chat = chatMemoryRepository.findById(Long.valueOf(conversationId)).orElseThrow();
    for (Message message : messages) {
      chat.addEntry(ChatEntry.toChatEntry(message, chat));
    }
    chatMemoryRepository.save(chat);
  }

  @Override
  public List<Message> get(@NonNull String conversationId) {
    var chat = chatMemoryRepository.findById(Long.valueOf(conversationId)).orElseThrow();
    return chat.getHistory().stream()
        .sorted(Comparator.comparing(ChatEntry::getCreatedAt).reversed())
        .map(ChatEntry::toMessage)
        .limit(maxMessages)
        .toList();
  }

  @Override
  public void clear(@NonNull String conversationId) {
    // not implemented
  }
}

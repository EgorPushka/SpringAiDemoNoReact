package com.example.springaidemonoreact.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.ai.chat.messages.Message;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    public static ChatEntry toChatEntry(Message message, Chat chat) {
        return ChatEntry.builder()
                .chat(chat)
                .role(Role.getRole(message.getMessageType().getValue()))
                .content(message.getText())
                .build();
    }

    public Message toMessage() {
        return role.getMessage(content);
    }
}

package com.example.springaidemonoreact.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("user") {
        @Override
        Message getMessage(String prompt) {
            return new UserMessage(prompt);
        }
    }, ASSISTANT("assistant") {
        @Override
        Message getMessage(String prompt) {
            return new AssistantMessage(prompt);
        }
    }, SYSTEM("system") {
        @Override
        Message getMessage(String prompt) {
            return new SystemMessage(prompt);
        }
    };

    private final String role;

    public static Role getRole (String name) {
        return Arrays.stream(Role.values()).filter(role -> role.role.equalsIgnoreCase(name)).findFirst().orElseThrow();
    }

    abstract Message getMessage(String prompt);

}

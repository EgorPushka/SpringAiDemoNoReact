package com.example.springaidemonoreact.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("user"), ASSISTANT("assistant"), SYSTEM("system");

    private final String role;

    public static Role getRole (String name) {
        return Arrays.stream(Role.values()).filter(role -> role.role.equalsIgnoreCase(name)).findFirst().orElseThrow();
    }

}

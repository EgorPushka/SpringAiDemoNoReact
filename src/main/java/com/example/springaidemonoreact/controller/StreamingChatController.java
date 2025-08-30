package com.example.springaidemonoreact.controller;

import com.example.springaidemonoreact.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class StreamingChatController {

    private final ChatService chatService;

    @GetMapping(value = "/chat-stream/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendToModel(@PathVariable Long chatId, @RequestParam("userPrompt") String userPrompt) {
        var emitter = chatService.processInteractionStreaming(chatId, userPrompt);
        return emitter;
    }

}

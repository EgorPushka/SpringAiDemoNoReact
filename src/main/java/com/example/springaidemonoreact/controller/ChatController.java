package com.example.springaidemonoreact.controller;

import com.example.springaidemonoreact.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/")
    public String mainPage(ModelMap model) {
        model.addAttribute("chats", chatService.getAllChats());
        return "chat";
    }

    @GetMapping("/chat/{chatId}")
    public String showChat(@PathVariable Long chatId, ModelMap model) {
        model.addAttribute("chats", chatService.getAllChats());
        model.addAttribute("chat", chatService.getChat(chatId));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String createNewChat(@RequestParam String title) {
        var newChat = chatService.createNewChat(title);
        return "redirect:/chat/" + newChat.getId();
    }

    @PostMapping("/chat/{chatId}/delete")
    public String deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return "redirect:/";
    }

//    @PostMapping("/chat/{chatId}/entry")
//    public String sendToModel(@PathVariable Long chatId, @RequestParam("userPrompt") String userPrompt) {
//        log.info("send to llm");
//        chatService.processInteraction(chatId, userPrompt);
//        return "redirect:/chat/" + chatId;
//    }

}

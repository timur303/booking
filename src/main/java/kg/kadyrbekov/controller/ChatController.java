package kg.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.Chat;
import kg.kadyrbekov.model.entity.Message;
import kg.kadyrbekov.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.ok(createdChat);
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<Message> sendMessage( @PathVariable Long chatId,
                                                @RequestBody Message message) {
        Message sentMessage = chatService.sendMessage(chatId, message);
        return ResponseEntity.ok(sentMessage);
    }
}

package kg.kadyrbekov.service;

import kg.kadyrbekov.model.entity.Chat;
import kg.kadyrbekov.model.entity.Message;
import kg.kadyrbekov.repository.ChatRepository;
import kg.kadyrbekov.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;

public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Chat createChat(Chat chat) {
        Chat savedChat = chatRepository.save(chat);
        return savedChat;
    }

    public Message sendMessage(Long chatId, Message message) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NotFoundException("Chat not found"));
        message.setChat(chat);
        Message sentMessage = messageRepository.save(message);
        return sentMessage;
    }

}

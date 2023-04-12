package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.MessageDto;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Chat;
import kg.kadyrbekov.model.entity.Message;
import kg.kadyrbekov.repository.ChatRepository;
import kg.kadyrbekov.repository.MessageRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    private final UserRepository userRepository;

    public Chat createChat(Chat chat) {
        Chat savedChat = chatRepository.save(chat);
        return savedChat;
    }


    public Message sendMessage(MessageDto messageDto) {
        User sender = userRepository.findByEmail(messageDto.getSenderEmail());
        User recipient = userRepository.findByEmail(messageDto.getRecipientEmail());
        if (sender == null || recipient == null) {
            throw new NotFoundException("User not found");
        }
        Chat chat = chatRepository.findByUser1AndUser2(Optional.of(sender), Optional.of(recipient));
        if (chat == null) {
            chat = new Chat(sender, recipient);
            chatRepository.save(chat);
        }
        Message message = new Message(sender, recipient, chat, messageDto.getContent());
        return messageRepository.save(message);
    }

    public Message sendMessage(Long chatId, Message message) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if (optionalChat.isEmpty()) {
            throw new RuntimeException("Chat not found for id " + chatId);
        }

        Chat chat = optionalChat.get();
        message.setChat(chat);
        messageRepository.save(message);

        return message;
    }
}

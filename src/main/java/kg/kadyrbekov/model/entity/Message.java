package kg.kadyrbekov.model.entity;

import kg.kadyrbekov.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    private LocalDateTime timestamp;

    public Message(User sender, User recipient, Chat chat, String content) {
        this.sender = sender;
        this.chat = chat;
    }
}

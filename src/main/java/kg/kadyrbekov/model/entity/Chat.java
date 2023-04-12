package kg.kadyrbekov.model.entity;

import kg.kadyrbekov.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    private List<User> participants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User user2;

    public Chat(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }


}

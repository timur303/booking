package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashMap;
import java.util.Map;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cabins")
public class Cabin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String description;

    private String price;

    private  int hours;

    @Transient
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    @OneToOne(mappedBy = "cabin", cascade = CascadeType.ALL)
    private Booking booking;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @JsonIgnore
    private Club club;

    @Transient
    private Long clubId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public  Map<String, Object> countdown() throws InterruptedException {
        int timeInMinutes = hours;
        int timeInSeconds = timeInMinutes * 60;
        for (int i = timeInSeconds; i >= 0; i--) {
            int minutesLeft = i / 60;
            int secondsLeft = i % 60;
            Map<String, Object> result = new HashMap<>();
            result.put("minutes", minutesLeft);
            result.put("seconds", secondsLeft);
            Thread.sleep(1000);
            return result;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Time's up!");
        return result;
    }
}

package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime endAt;

    private double hours;

    private int minutes;

    private double cost;

    private String remainingTime;

    private String response;

    @OneToOne
    @JoinColumn(name = "cabin_id")
    @JsonIgnore
    private Cabin cabin;
    @Transient
    private Long cabinId;


    @OneToOne
    @JoinColumn(name = "computer_id")
    private Computer computer;
    @Transient
    private Long computerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @Transient
    private Long userId;

}

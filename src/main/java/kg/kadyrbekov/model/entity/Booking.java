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
    private LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "cabin_id")
    private Cabin cabin;
    @Transient
    private Long cabinId;


    @OneToOne
    @JoinColumn(name = "computer_id")
    private Computer computer;
    @Transient
    private Long computerId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User userId;


}

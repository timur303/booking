package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.model.enums.Night;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "computers")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String description;

    private double price;

    private double nightPrice;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Booking booking;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
    @Transient
    private Long clubId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @Transient
    Long userId;


}

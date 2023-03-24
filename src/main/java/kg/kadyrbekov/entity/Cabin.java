package kg.kadyrbekov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.entity.enums.ClubStatus;
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
@Table(name = "cabins")
public class Cabin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String description;

    private String price;

    private boolean isBooked;

    private String review;

    @Transient
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    @OneToOne(mappedBy = "cabin", cascade = CascadeType.ALL)
    private Booking booking;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


}

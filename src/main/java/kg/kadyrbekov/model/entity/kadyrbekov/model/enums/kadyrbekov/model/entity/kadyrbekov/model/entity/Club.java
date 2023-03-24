package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clubName;

    private String managerName;

    private Long phoneNumber;

    private String description;

    private String logo;

    private String review;

    private String comment;

    @Enumerated(EnumType.STRING)
    private City city;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
//            CascadeType.MERGE})
//    @JoinTable(name = "club_cabin", joinColumns = @JoinColumn(name = "clubs_id")
//            , inverseJoinColumns = @JoinColumn(name = "cabins_id"))
    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Cabin> cabins;

    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Computer> computers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Transient
    private Long userId;

    @Transient
    private Long cabinId;
}

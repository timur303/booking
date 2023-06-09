package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.City;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ApiModel(description = "The model representing a Club")
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

    private Long showing;

    private String state;

    private String street;

    private String homeNumber;

    @OneToMany(mappedBy = "club")
    private List<Photo> photos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Cabin> cabins;
    @Transient
    private Long cabinId;


    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Computer> computers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Transient
    private Long userId;

    @OneToMany(mappedBy = "club")
    private List<Review> reviews;

    private Long clubManagerId;
}

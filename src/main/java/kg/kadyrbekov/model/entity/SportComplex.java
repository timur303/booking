package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sportComplex")
public class SportComplex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String complexName;

    private String managerName;

    private Long phoneNumber;

    private String description;

    private String logo;

    private Long showing;

    private String state;//микро район -> ХБК, Зайнабидин

    private String street;

    private String homeNumber;

    @OneToMany(mappedBy = "sportComplex")
    private List<Photo> photos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Turf> turfs;

    @OneToMany(fetch = FetchType.LAZY, cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JsonIgnore
    private List<Volleyball> volleyballs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @Transient
    private Long userId;

    private Long complexManagerId;

    @OneToMany(mappedBy = "sportComplex")
    private List<Review> reviews;



}

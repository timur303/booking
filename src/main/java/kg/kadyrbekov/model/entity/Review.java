package kg.kadyrbekov.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@ApiModel(description = "The model representing a Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StarRating starRating;

    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Transient
    private Long userId;

    @ManyToOne
    private Club club;

    @Transient
    private Long clubId;

    @ManyToOne
    private SportComplex sportComplex;

    @Transient
    private Long complexId;
}

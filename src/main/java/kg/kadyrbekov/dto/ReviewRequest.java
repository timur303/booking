package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Getter
@Setter
public class ReviewRequest {

    @Enumerated(EnumType.STRING)
    private StarRating starRating;

    private String review;

//    private Long userId;

    private User user;

    private Club clubs;

    @Transient
    private Long clubId;
}

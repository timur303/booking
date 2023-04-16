package kg.kadyrbekov.dto;

import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.City;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClubRequest {

    private String clubName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private City city;

    private User user;

    private List<Review> reviews;

    private Long cabinId;

    private String state;

    private String street;

    private String homeNumber;

    private Long clubManagerId;

    private List<Photo> photos;


}

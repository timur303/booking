package kg.kadyrbekov.dto;


import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.ClubStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
public class VolleyballRequest {

    private String name;

    private String image;

    private String description;

    private double price;

    private User user;

    private double nightPrice;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long complexId;

    private List<Photo> photos;

    private List<Review> reviews;

}

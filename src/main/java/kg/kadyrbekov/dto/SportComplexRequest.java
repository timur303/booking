package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
public class SportComplexRequest {

    private String complexName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private City city;

    private User user;

    private List<Review> reviews;

    private String state;

    private Long complexManagerId;

    private String street;

    private String homeNumber;

    private List<Photo> photos;

}

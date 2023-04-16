package kg.kadyrbekov.dto;

import io.swagger.annotations.ApiModel;
import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.City;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("clubResponse")
public class ClubResponse {

    private Long id;

    private String clubName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private City city;

    private String state;

    private String street;

    private String homeNumber;

    private Long userId;

    private Long clubManagerId;

    private List<Photo> photos;

    private List<Review> reviews;
}

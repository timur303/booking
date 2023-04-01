package kg.kadyrbekov.dto;

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
public class ClubResponse {

    private Long id;

    private String clubName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private City city;

    private List<Review> reviews;

    private String state;

    private String street;

    private String homeNumber;

}

package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.City;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportComplexResponse {

    @Id
    private Long id;

    private String complexName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    @Enumerated(EnumType.STRING)
    private City city;

    private Long complexManagerId;

    private String state;

    private String street;

    private String homeNumber;

    private Long userId;

    private List<Photo> photos;

    private List<Review> reviews;

}

package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.Photo;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.model.enums.Night;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurfResponse {

    private Long id;

    private String name;

    private String image;

    @Enumerated(EnumType.STRING)
    private Night night;

    private double nightPrice;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long userId;

    private Long complexId;

    private List<Photo> photos;

    private List<Review> reviews;

}

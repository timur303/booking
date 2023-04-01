package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.enums.StarRating;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private StarRating starRating;

    private String review;

    private Long clubId;
}

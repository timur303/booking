package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.enums.ClubStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinResponse {

    private Long id;

    private String name;

    private String image;

    private String description;

    private String price;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long userId;

    private Long clubId;
}

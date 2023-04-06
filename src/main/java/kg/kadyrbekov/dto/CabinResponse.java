package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.model.enums.Night;
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

    @Enumerated(EnumType.STRING)
    private Night night;

    private double nightPrice;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long userId;

    private Long clubId;
}

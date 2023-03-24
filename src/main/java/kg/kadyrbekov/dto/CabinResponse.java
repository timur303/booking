package kg.kadyrbekov.dto;

import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.entity.enums.ClubStatus;
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

    private boolean isBooked;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long userId;

    private Long clubId;
}

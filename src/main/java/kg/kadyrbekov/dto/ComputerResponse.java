package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.enums.ClubStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComputerResponse {

    private Long id;

    private String name;

    private String image;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long userId;

    private Long clubId;

}

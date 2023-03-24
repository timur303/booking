package kg.kadyrbekov.dto;

import kg.kadyrbekov.entity.enums.ClubStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Builder
public class ComputerResponse {

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

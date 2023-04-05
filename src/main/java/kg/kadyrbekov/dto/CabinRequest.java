package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.model.enums.Night;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class CabinRequest {

    private String name;

    private String image;

    private String description;

    private double price;

    private User user;

    @Enumerated(EnumType.STRING)
    private Night night;

    private int night1;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long clubId;
}

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
public class ComputerRequest {

    private String name;

    private String image;

    private String description;

    private double price;

    private User user;

    @Enumerated(EnumType.STRING)
    private Night night;

    private double nightPrice;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private Long clubId;

}

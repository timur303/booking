package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
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

    private String price;

    private Integer hours;

    private Long userId;

    private User user;

    private Long clubId;


}

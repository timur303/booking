package kg.kadyrbekov.model.entity.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class ClubRequest {

    private String clubName;

    private Long phoneNumber;

    private String managerName;

    private String description;

    private String logo;

    private String review;

    private String comment;

    @Enumerated(EnumType.STRING)
    private City city;

    private User user;

    private Long userId;

    private Long cabinId;

}

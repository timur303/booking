package kg.kadyrbekov.dto;

import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.entity.enums.City;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubResponse {

    private Long id;

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
}

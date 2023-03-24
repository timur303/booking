package kg.kadyrbekov.dto;

import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.entity.enums.ClubStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class CabinRequest {

    private String name;

    private boolean isBooked;

    @Enumerated(EnumType.STRING)
    private ClubStatus clubStatus;

    private User user;

    private Long userId;
}

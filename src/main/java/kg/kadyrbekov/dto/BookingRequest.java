package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.enums.Night;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime endAt;

    private int hours;

    private int minutes;

    @Enumerated(EnumType.STRING)
    private Night night;

    private double cost;

    private Cabin cabin;

    private Computer computer;

    private User user;
}


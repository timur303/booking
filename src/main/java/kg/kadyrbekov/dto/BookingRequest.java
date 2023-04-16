package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.enums.Night;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {

    private LocalDateTime bookingTime;

    private LocalDateTime from;

    private LocalDateTime to;

    @Enumerated(EnumType.STRING)
    private Night night;

    private Cabin cabin;

    private Computer computer;

    private User user;
}


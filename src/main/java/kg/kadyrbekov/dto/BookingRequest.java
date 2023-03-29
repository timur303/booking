package kg.kadyrbekov.dto;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime endAt;

    private double hours;

    private int minutes;

    private double cost;

    private Cabin cabin;

    private Long cabinId;

    private Computer computer;

    private Long computerId;

    private User user;

    Long userId;

}


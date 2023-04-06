package kg.kadyrbekov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.enums.Night;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private String message;

    private Booking booking;

    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedDate
    private LocalDateTime endAt;

    private int hours;

    private int minutes;

    private double cost;

    private Cabin cabin;

    private Long cabinId;

    private Computer computer;

    private Long computerId;

    private User user;

    private Long userId;

}

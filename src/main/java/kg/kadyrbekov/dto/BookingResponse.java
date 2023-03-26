package kg.kadyrbekov.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private LocalDate createdAt;

    private int hours;

    private Cabin cabin;

    private Long cabinId;

    private Computer computer;

    private Long computerId;

    private User user;

    private Long userId;

}

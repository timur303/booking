package kg.kadyrbekov.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;
    private String message;
    private LocalDateTime from;
    private LocalDateTime to;
    private double totalPrice;

//    private User user;

    private Long userId;


    public BookingResponse(String message) {
        this.message = message;
    }

    public BookingResponse(String message, LocalDateTime from, LocalDateTime to, double totalPrice) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.totalPrice = totalPrice;
    }

}

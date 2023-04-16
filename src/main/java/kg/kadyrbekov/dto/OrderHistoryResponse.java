package kg.kadyrbekov.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryResponse {

    private Long Id;

    private Long bookingId;

    private Long userId;

    private double price;


}


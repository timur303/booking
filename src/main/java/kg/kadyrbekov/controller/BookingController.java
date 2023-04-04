package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.BookingRequest;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/cabins/{id}")
    public ResponseEntity<Booking> bookCabins(@RequestBody BookingRequest bookingRequest, @PathVariable Long id) throws InterruptedException {
        Booking booking = bookingService.bookCabins(bookingRequest, id);
        String message = "Wait for " + booking.getCost() + " seconds.";
        return ResponseEntity.ok().header("Message", message).body(booking);
    }

    @PostMapping("/comps/{id}")
    public ResponseEntity<Booking> bookComps(@RequestBody BookingRequest bookingRequest, @PathVariable Long id) throws InterruptedException {
        Booking booking = bookingService.bookComps(bookingRequest, id);
        String message = "Wait for " + booking.getCost() + " seconds.";
        return ResponseEntity.ok().header("Message", message).body(booking);
    }

    @DeleteMapping("/unBook/{cabinId}")
    public void cancelBooking(@PathVariable Long cabinId) {
        bookingService.cancelBooking(cabinId);
    }


}

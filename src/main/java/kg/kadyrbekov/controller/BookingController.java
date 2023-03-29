package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.BookingRequest;
import kg.kadyrbekov.dto.BookingResponse;
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

//    @PostMapping("/cabin/{cabinId}")
//    public Booking bookCabin(@RequestBody BookingRequest request, @PathVariable Long cabinId) throws InterruptedException {
//        return bookingService.bookCabins1(request,cabinId);
//    }

    @PostMapping("/cabins/{id}")
    public ResponseEntity<Booking> bookCabins1(@RequestBody BookingRequest bookingRequest, @PathVariable Long id) throws InterruptedException {
        Booking booking = bookingService.bookCabins1(bookingRequest, id);
        String message = "Wait for " + booking.getRemainingTime() + " seconds.";
        return ResponseEntity.ok().header("Message", message).body(booking);
    }

    @PostMapping("/comp/{cabinId}")
    public BookingResponse bookComp(@PathVariable Long cabinId, @RequestParam int hour, @RequestParam int minute) throws InterruptedException {
        return bookingService.bookComps(cabinId, hour, minute);
    }

    @DeleteMapping("/unBook/{cabinId}")
    public void cancelBooking(@PathVariable Long cabinId) {
        bookingService.cancelBooking(cabinId);
    }


}

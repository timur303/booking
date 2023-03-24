package kg.kadyrbekov.model.entity.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.kadyrbekov.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("cabin/{id}")
    public Booking bookingCabin(@PathVariable Long id) {
        return bookingService.bookCabin(id);
    }

    @PostMapping("comp/{id}")
    public Booking bookingComp(@PathVariable Long id) {
        return bookingService.bookComputer(id);
    }
}

package kg.kadyrbekov.controller;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.repository.BookingRepository;
import kg.kadyrbekov.repository.CabinRepository;
import kg.kadyrbekov.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;

@RestController
@RequestMapping("api/book")
public class BookingController {

    private final CabinRepository cabinRepository;
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    public BookingController(CabinRepository cabinRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.cabinRepository = cabinRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/cabin/{cabinId}")
    public Booking bookCabin(@PathVariable Long cabinId, @RequestParam int hour, @RequestParam int minute) throws InterruptedException {
        User user = getPrinciple();
        Cabin cabin = cabinRepository.findById(cabinId).orElseThrow(() -> new NotFoundException("Cabin with id not found " + cabinId));
        if (cabin.getClubStatus().equals(ClubStatus.BOOKED)) {
            throw new RuntimeException("Cabin already booked");
        } else if (cabin.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
            Booking booking = new Booking();
            booking.setCreatedAt(LocalDate.from(LocalDate.now(Clock.systemDefaultZone())));
            booking.setEndAt(LocalDate.from(LocalDate.now(Clock.systemDefaultZone())));
            booking.setCabin(cabin);
            booking.setUser(user);
            bookingRepository.save(booking);
            cabin.setClubStatus(ClubStatus.BOOKED);
            cabinRepository.save(cabin);
            Thread thread = new Thread(() -> {
                try {
                    countdown(hour,minute);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cabin.setClubStatus(ClubStatus.NOT_BOOKED);
                cabinRepository.save(cabin);
            });
            thread.start();
            return booking;
        } else {
            throw new RuntimeException("Cabin status not supported");
        }
    }

    @DeleteMapping("unBook/{bookingId}")
    public ResponseEntity<String> unBookCabin(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
            Cabin cabin = booking.getCabin();

            if (cabin.getClubStatus() != ClubStatus.BOOKED) {
                return ResponseEntity.badRequest().body("Cabin is not currently booked");
            }

            bookingRepository.delete(booking);
            cabin.setClubStatus(ClubStatus.NOT_BOOKED);
            cabinRepository.save(cabin);

            return ResponseEntity.ok("Booking cancelled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error cancelling booking: " + e.getMessage());
        }
    }

    private User getPrinciple() {
        // Retrieve the current user from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                });
    }


    public void countdown(int minutes) throws InterruptedException {
        int seconds = 0;
        int hours = 0;
        int totalSeconds = hours * 3600 + minutes * 60 + seconds;
        while (totalSeconds > 0) {
            int remainingHours = totalSeconds / 3600;
            int remainingMinutes = (totalSeconds % 3600) / 60;
            int remainingSeconds = totalSeconds % 60;
            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
            Thread.sleep(1000);
            totalSeconds--;
        }

        System.out.println("Time's up!");
        int cost = minutes * 2;
        System.out.println("Your check " + cost + " $ ");
    }

    public void countdown(int hours, int minutes) throws InterruptedException {
        int seconds = 0;
        int totalSeconds = hours * 3600 + minutes * 60 + seconds;
        while (totalSeconds > 0) {
            int remainingHours = totalSeconds / 3600;
            int remainingMinutes = (totalSeconds % 3600) / 60;
            int remainingSeconds = totalSeconds % 60;
            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
            Thread.sleep(1000);
            totalSeconds--;
        }

        System.out.println("Time's up!");
        int cost = minutes * 2;
        System.out.println("Your check " + cost + " $ ");
    }
}

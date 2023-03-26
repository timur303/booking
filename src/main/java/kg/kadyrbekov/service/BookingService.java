package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.BookingResponse;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookingService {

    private final CabinRepository cabinRepository;
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    public BookingService(CabinRepository cabinRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.cabinRepository = cabinRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking bookCabin(Long cabinId, int hours) throws InterruptedException {
        User user = getCurrentUser();
        Cabin cabin = cabinRepository.findById(cabinId).orElseThrow(() -> new NotFoundException("Cabin with id " + cabinId + " not found"));

        if (cabin.getClubStatus() == ClubStatus.BOOKED) {
            throw new RuntimeException("Cabin is already booked");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(hours);
        Booking booking = new Booking();
        booking.setCabin(cabin);
        booking.setUser(user);
        booking.setCreatedAt(LocalDate.from(now));
        booking.setEndAt(LocalDate.from(end));
        bookingRepository.save(booking);

        cabin.setClubStatus(ClubStatus.BOOKED);
        cabinRepository.save(cabin);

        startCountdownTimer(cabin, hours);

        return booking;
    }

    private void startCountdownTimer(Cabin cabin, int hours) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(hours * 60 * 60 * 1000);
                cabin.setClubStatus(ClubStatus.NOT_BOOKED);
                cabinRepository.save(cabin);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private User getCurrentUser() {
        // implementation to get the current user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                });
    }


    public User getPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                });
    }


    public Booking bookCabin(Long cabinId) throws InterruptedException {
        User user = getPrinciple();
        Cabin cabin = cabinRepository.findById(cabinId).orElseThrow(() -> new NotFoundException("cabin with id not found " + cabinId));
        Booking booking = new Booking();
        if (cabin.getClubStatus().equals(ClubStatus.BOOKED)) {
            throw new RuntimeException("Already booked");
        } else if (cabin.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
        } else {
            booking.setId(booking.getId());
            booking.setCreatedAt(LocalDate.now());
            booking.setCabin(cabin);
            booking.setUser(user);
            bookingRepository.save(booking);
            cabin.setClubStatus(ClubStatus.BOOKED);
            cabinRepository.save(cabin);
        }

        return booking;
    }

//    public Booking bookComputer(Long computerId) {
//        User user = new User();
//        Computer computer = computerRepository.findById(computerId).orElseThrow(() -> new NotFoundException("Computer with id not found  " + computerId));
//        Booking booking = new Booking();
//        if (computer.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
//            booking.setId(booking.getId());
//            booking.setCreatedAt(LocalDate.now());
//            booking.setComputer(computer);
//            booking.setUserId(user.getId());
//            bookingRepository.save(booking);
//            computer.setClubStatus(ClubStatus.BOOKED);
//            computerRepository.save(computer);
//        }
//        return booking;
//    }

    public void unBookCabin(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Cabin cabin = booking.getCabin();

        if (cabin.getClubStatus() != ClubStatus.BOOKED) {
            throw new RuntimeException("Cabin is not currently booked");
        }

        bookingRepository.delete(booking);
        cabin.setClubStatus(ClubStatus.NOT_BOOKED);
        cabinRepository.save(cabin);
    }
    public static Map<String, Object> countdown() throws InterruptedException {
        int timeInMinutes = 60;
        int timeInSeconds = timeInMinutes * 60;
        for (int i = timeInSeconds; i >= 0; i--) {
            int minutesLeft = i / 60;
            int secondsLeft = i % 60;
            Map<String, Object> result = new HashMap<>();
            result.put("minutes", minutesLeft);
            result.put("seconds", secondsLeft);
            Thread.sleep(1000);
            return result;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Time's up!");
        return result;
    }

    public BookingResponse bookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .hours(booking.getHours())
                .createdAt(booking.getCreatedAt())
                .userId(booking.getUserId())
                .computerId(booking.getComputerId())
                .cabinId(booking.getCabinId())
                .build();
    }
}

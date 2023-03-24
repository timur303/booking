package kg.kadyrbekov.service;

import kg.kadyrbekov.entity.Booking;
import kg.kadyrbekov.entity.Cabin;
import kg.kadyrbekov.entity.Computer;
import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.entity.enums.ClubStatus;
import kg.kadyrbekov.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;

    private final CabinRepository cabinRepository;

    private final ComputerRepository computerRepository;

    private final BookingRepository bookingRepository;


    public User getPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                });
    }

    public Booking bookCharity(Long cabinId) {
        User user = getPrinciple();
        Cabin cabin = cabinRepository.findById(cabinId).orElseThrow(() -> new NotFoundException("cabin with id not found " + cabinId));
        Booking booking = new Booking();
        if (cabin.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
            booking.setId(booking.getId());
            booking.setCreatedAt(LocalDate.now());
            booking.setCabin(cabin);
            booking.setUserId(user);
            bookingRepository.save(booking);
            cabin.setClubStatus(ClubStatus.BOOKED);
            cabinRepository.save(cabin);
        }
        return booking;
    }

    public Booking bookWishlist(Long computerId) {
        User user = getPrinciple();
        Computer computer = computerRepository.findById(computerId).orElseThrow(() -> new NotFoundException("Computer with id not found  " + computerId));
        Booking booking = new Booking();
        if (computer.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
            booking.setId(booking.getId());
            booking.setCreatedAt(LocalDate.now());
            booking.setComputer(computer);
            booking.setUserId(user);
            bookingRepository.save(booking);
            computer.setClubStatus(ClubStatus.BOOKED);
            computerRepository.save(computer);
        }
        return booking;
    }

}

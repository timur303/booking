package kg.kadyrbekov.service;

import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.ClubStatus;
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


    public User userById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id not found"));
    }

    public Booking bookCabin(Long cabinId) {
        User user = new User();
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

    public Booking bookComputer(Long computerId) {
        User user = new User();
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

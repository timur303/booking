package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.BookingRequest;
import kg.kadyrbekov.dto.BookingResponse;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.repository.BookingRepository;
import kg.kadyrbekov.repository.CabinRepository;
import kg.kadyrbekov.repository.ComputerRepository;
import kg.kadyrbekov.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {

    private final Map<Long, Thread> runningTimers = new ConcurrentHashMap<>();
    private final CabinRepository cabinRepository;
    private final BookingRepository bookingRepository;
    private final ComputerRepository computerRepository;
    private final UserRepository userRepository;

    public BookingService(CabinRepository cabinRepository, BookingRepository bookingRepository, ComputerRepository computerRepository, UserRepository userRepository) {
        this.cabinRepository = cabinRepository;
        this.bookingRepository = bookingRepository;
        this.computerRepository = computerRepository;
        this.userRepository = userRepository;
    }


    public void cancelBooking(Long cabinId) {
        Thread thread = runningTimers.get(cabinId);
        if (thread != null) {
            thread.interrupt();
            runningTimers.remove(cabinId);
        }
        Cabin cabin = cabinRepository.findById(cabinId)
                .orElseThrow(() -> new NotFoundException("Cabin with id not found " + cabinId));
        if (cabin.getClubStatus().equals(ClubStatus.BOOKED)) {
            cabin.setClubStatus(ClubStatus.NOT_BOOKED);
            cabinRepository.save(cabin);
        }
    }



    public Booking bookCabins1(BookingRequest bookingRequest, Long id) throws InterruptedException {
        User user = getPrinciple();
        Cabin cabin = cabinRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cabin with id not found " + bookingRequest.getCabinId()));
        if (cabin.getClubStatus().equals(ClubStatus.BOOKED)) {
            throw new RuntimeException("Cabin already booked");
        } else if (cabin.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
            Booking booking = new Booking();
            booking.setCreatedAt(LocalDateTime.now());
            booking.setCabin(cabin);
            booking.setUser(user);
            booking.setCabinId(bookingRequest.getCabinId());
            booking.setUserId(bookingRequest.getUserId());
            bookingRepository.save(booking);
            cabin.setClubStatus(ClubStatus.BOOKED);
            cabinRepository.save(cabin);

            int seconds = 0;
            double totalSeconds = bookingRequest.getHours() * 3600 + bookingRequest.getMinutes() * 60 + seconds;
            while (totalSeconds > 0) {
                int remainingHours = (int) (totalSeconds / 3600);
                int remainingMinutes = (int) ((totalSeconds % 3600) / 60);
                int remainingSeconds = (int) (totalSeconds % 60);
                String remainingTime = String.format("%02d:%02d:%02d", remainingHours, remainingMinutes, remainingSeconds);
                booking.setRemainingTime(remainingTime);
                System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
                Thread.sleep(1000);
                totalSeconds--;
            }

            System.out.println("Time's up!");
            cabin.setClubStatus(ClubStatus.NOT_BOOKED);
            cabinRepository.save(cabin);

            double cost = (bookingRequest.getMinutes() / 60.0) * cabin.getPrice();
            String costInfo = "Your check " + cost + " $ ";
            booking.setCost(cost);
            booking.setMinutes(bookingRequest.getMinutes());
            booking.setEndAt(LocalDateTime.now());
            booking.setResponse(costInfo);
            bookingRepository.save(booking);

            return booking;

        } else {
            throw new RuntimeException("Cabin status not supported");
        }
    }

    public BookingResponse bookComps(Long compId, int hour, int minute) throws InterruptedException {
        User user = getPrinciple();
        Computer computer = computerRepository.findById(compId).orElseThrow(() -> new NotFoundException("Computer with id not found " + compId));
        if (computer.getClubStatus().equals(ClubStatus.BOOKED)) {
            throw new RuntimeException("Computer already booked");
        } else if (computer.getClubStatus().equals(ClubStatus.NOT_BOOKED)) {
            Booking booking = new Booking();
            booking.setCreatedAt(LocalDateTime.now());
            booking.setComputer(computer);
            booking.setUser(user);
            bookingRepository.save(booking);
            computer.setClubStatus(ClubStatus.BOOKED);
            computerRepository.save(computer);


            int seconds = 0;
            double totalSeconds = hour * 3600 + minute * 60 + seconds;
            while (totalSeconds > 0) {
                int remainingHours = (int) (totalSeconds / 3600);
                int remainingMinutes = (int) ((totalSeconds % 3600) / 60);
                int remainingSeconds = (int) (totalSeconds % 60);
                System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
                Thread.sleep(1000);
                totalSeconds--;
            }
            System.out.println("Time's up!");
            computer.setClubStatus(ClubStatus.NOT_BOOKED);
            computerRepository.save(computer);

            double cost = (minute / 60.0) * computer.getPrice();
            System.out.println("Your check " + cost + " $ ");
            booking.setCost(cost);
            booking.setMinutes(minute);
            booking.setEndAt(LocalDateTime.now());
            bookingRepository.save(booking);

            return bookingResponse(booking);
        } else {
            throw new RuntimeException("Computer status not supported");
        }
    }


    public void countdown(int hours, int minutes) throws InterruptedException {
        int seconds = 0;
        double totalSeconds = hours * 3600 + minutes * 60 + seconds;
        while (totalSeconds > 0) {
            int remainingHours = (int) (totalSeconds / 3600);
            int remainingMinutes = (int) ((totalSeconds % 3600) / 60);
            int remainingSeconds = (int) (totalSeconds % 60);
            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
            Thread.sleep(1000);
            totalSeconds--;
        }
        System.out.println("Time's up!");
        Cabin cabin = cabinRepository.findById(1L).get();


        double cost = (minutes / 60.0) * cabin.getPrice();
        System.out.println("Your check " + cost + " $ ");
        Booking booking = bookingRepository.findById(1L).get();
        booking.setCost(cost);
        booking.setMinutes(minutes);
        bookingRepository.save(booking);

    }

    public User getPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                });
    }


    public BookingResponse bookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .hours(booking.getHours())
                .createdAt(booking.getCreatedAt())
                .endAt(booking.getEndAt())
                .userId(booking.getUserId())
                .computerId(booking.getComputerId())
                .cabinId(booking.getCabinId())
                .cost(booking.getCost())
                .minutes(booking.getMinutes())
                .build();
    }
}

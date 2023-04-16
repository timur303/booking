package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.BookingRequest;
import kg.kadyrbekov.dto.BookingResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.exception.TimeExpiredException;
import kg.kadyrbekov.exception.TurfAlreadyBookedException;
import kg.kadyrbekov.exception.UserBlockedException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.*;
import kg.kadyrbekov.model.enums.ClubStatus;
import kg.kadyrbekov.model.enums.Night;
import kg.kadyrbekov.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final Map<Long, Thread> runningTimers = new ConcurrentHashMap<>();
    private final CabinRepository cabinRepository;
    private final BookingRepository bookingRepository;
    private final ComputerRepository computerRepository;
    private final UserRepository userRepository;

    private final VolleyballRepository volleyballRepository;

    private final TurfRepository turfRepository;

    private final OrderHistoryService orderHistoryService;

    public BookingResponse bookCabins(BookingRequest bookingRequest, List<Long> cabinsId) throws TimeExpiredException, UserBlockedException {
        List<Cabin> cabins = cabinRepository.findAllById(cabinsId);

        LocalDateTime from = bookingRequest.getFrom();
        LocalDateTime to = bookingRequest.getTo();
        Night night = bookingRequest.getNight();

        double totalPrice = 0;
        Booking booking = null;

        for (Cabin cabin : cabins) {
            if (cabin.getClubStatus() == ClubStatus.BOOKED) {
                throw new TurfAlreadyBookedException("Cabin with id " + cabin.getId() + " is already booked and cannot be booked again.");
            }
            if (to.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking time is expired");
            }
            if (from.isAfter(to)) {
                throw new TimeExpiredException("Booking end time is before start time");
            }
            if (from.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking start time is overdue");
            }

            Duration duration = Duration.between(from, to);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() - (hours * 60);

            double cabinPrice = 0;
            if (night == Night.NIGHT) {
                cabinPrice = (hours * 60 + minutes) * (cabin.getNightPrice() / 60);
            } else {
                cabinPrice = (hours * 60 + minutes) * (cabin.getPrice() / 60);
            }

            totalPrice += cabinPrice;

            User user = getPrinciple();
            if (user.isBlocked()) {
                throw new UserBlockedException("User is blocked and cannot book cabins.");
            }

            booking = new Booking();
            booking.setFroms(from);
            booking.setBto(to);
            booking.setShowDate(new Date());
            booking.setResponse("OK");
            booking.setTotalPrice(cabinPrice);
            booking.setCabin(cabin);
            booking.setUser(user);

            cabin.setClubStatus(ClubStatus.BOOKED);

            Booking bookingSave = bookingRepository.save(booking);
            cabinRepository.save(cabin);

            orderHistoryService.createOrderHistory(bookingSave, LocalDateTime.now());

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    cabin.setClubStatus(ClubStatus.NOT_BOOKED);
                    cabinRepository.save(cabin);
                }
            }, Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setMessage("Booking successful for " + cabins.size() + " cabins");
        bookingResponse.setFrom(from);
        bookingResponse.setTo(to);
        bookingResponse.setTotalPrice(totalPrice);

        if (booking != null) {
            bookingResponse.setId(booking.getId());
            bookingResponse.setUserId(booking.getUser().getId());
        }

        return bookingResponse;
    }


    public Booking getByID(Long id) {
        return bookingRepository.findById(id).get();
    }

    public void cancelBooking(Long cabinId) throws NotFoundException {
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


    public BookingResponse bookTurfs(BookingRequest bookingRequest, List<Long> turfIds) throws TimeExpiredException, UserBlockedException {
        List<Turf> turfs = turfRepository.findAllById(turfIds);
        LocalDateTime from = bookingRequest.getFrom();
        LocalDateTime to = bookingRequest.getTo();
        Night night = bookingRequest.getNight();

        double totalPrice = 0;
        Booking booking = null;

        for (Turf turf : turfs) {
            if (turf.getClubStatus() == ClubStatus.BOOKED) {
                throw new TurfAlreadyBookedException("Turf with id " + turf.getId() + " is already booked and cannot be booked again.");
            }
            if (to.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking time is expired");
            }
            if (from.isAfter(to)) {
                throw new TimeExpiredException("Booking end time is before start time");
            }
            if (from.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking start time is overdue");
            }

            Duration duration = Duration.between(from, to);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() - (hours * 60);

            double turfPrice = 0;
            if (night == Night.NIGHT) {
                turfPrice = (hours * 60 + minutes) * (turf.getNightPrice() / 60);
            } else {
                turfPrice = (hours * 60 + minutes) * (turf.getPrice() / 60);
            }

            totalPrice += turfPrice;

            User user = getPrinciple();
            booking = new Booking();
            if (user.isBlocked()) {
                throw new UserBlockedException("User is blocked and cannot book turf hall");
            }

            booking.setFroms(from);
            booking.setBto(to);
            booking.setShowDate(new Date());
            booking.setResponse("OK");
            booking.setTotalPrice(turfPrice);
            booking.setUser(user);
            booking.setTurf(turf);

            turf.setClubStatus(ClubStatus.BOOKED);

            Booking bookingSave = bookingRepository.save(booking);
            turfRepository.save(turf);
            orderHistoryService.createOrderHistory(bookingSave, LocalDateTime.now());

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    turf.setClubStatus(ClubStatus.NOT_BOOKED);
                    turfRepository.save(turf);
                }
            }, Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setMessage("Booking successful for " + turfs.size() + " turfs");
        bookingResponse.setFrom(from);
        bookingResponse.setTo(to);
        bookingResponse.setTotalPrice(totalPrice);

        if (booking != null) {
            bookingResponse.setId(booking.getId());
            bookingResponse.setUserId(booking.getUser().getId());
        }

        return bookingResponse;
    }

    public BookingResponse bookVolleyballs(BookingRequest bookingRequest, List<Long> volleyballIds) throws TimeExpiredException, UserBlockedException {
        List<Volleyball> volleyballs = volleyballRepository.findAllById(volleyballIds);

        LocalDateTime from = bookingRequest.getFrom();
        LocalDateTime to = bookingRequest.getTo();
        Night night = bookingRequest.getNight();

        double totalPrice = 0;
        Booking booking = null;

        for (Volleyball volleyball : volleyballs) {
            if (volleyball.getClubStatus() == ClubStatus.BOOKED) {
                throw new TurfAlreadyBookedException("Volleyball with id " + volleyball.getId() + " is already booked and cannot be booked again.");
            }
            if (to.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking time is expired");
            }
            if (from.isAfter(to)) {
                throw new TimeExpiredException("Booking end time is before start time");
            }
            if (from.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking start time is overdue");
            }

            Duration duration = Duration.between(from, to);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() - (hours * 60);

            double volleyballPrice = 0;
            if (night == Night.NIGHT) {
                volleyballPrice = (hours * 60 + minutes) * (volleyball.getNightPrice() / 60);
            } else {
                volleyballPrice = (hours * 60 + minutes) * (volleyball.getPrice() / 60);
            }

            totalPrice += volleyballPrice;

            User user = getPrinciple();
            booking = new Booking();
            if (user.isBlocked()) {
                throw new UserBlockedException("User is blocked and cannot book volleyball hall");
            }
            booking.setFroms(from);
            booking.setBto(to);
            booking.setShowDate(new Date());
            booking.setResponse("OK");
            booking.setTotalPrice(volleyballPrice);
            booking.setUser(user);
            booking.setVolleyball(volleyball);

            volleyball.setClubStatus(ClubStatus.BOOKED);

            Booking bookingSave = bookingRepository.save(booking);
            volleyballRepository.save(volleyball);

            orderHistoryService.createOrderHistory(bookingSave, LocalDateTime.now());

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    volleyball.setClubStatus(ClubStatus.NOT_BOOKED);
                    volleyballRepository.save(volleyball);
                }
            }, Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setMessage("Booking successful for " + volleyballs.size() + " volleyballs");
        bookingResponse.setFrom(from);
        bookingResponse.setTo(to);
        bookingResponse.setTotalPrice(totalPrice);

        if (booking != null) {
            bookingResponse.setId(booking.getId());
            bookingResponse.setUserId(booking.getUser().getId());
        }

        return bookingResponse;
    }

    public BookingResponse bookComputers(BookingRequest bookingRequest, List<Long> computersId) throws TimeExpiredException, UserBlockedException {
        List<Computer> computers = computerRepository.findAllById(computersId);

        LocalDateTime from = bookingRequest.getFrom();
        LocalDateTime to = bookingRequest.getTo();
        Night night = bookingRequest.getNight();

        double totalPrice = 0;
        Booking booking = new Booking();

        for (Computer computer : computers) {
            if (computer.getClubStatus() == ClubStatus.BOOKED) {
                throw new TurfAlreadyBookedException("Computer with id " + computer.getId() + " is already booked and cannot be booked again.");
            }
            if (to.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking time is expired");
            }
            if (from.isAfter(to)) {
                throw new TimeExpiredException("Booking end time is before start time");
            }
            if (from.isBefore(LocalDateTime.now())) {
                throw new TimeExpiredException("Booking start time is overdue");
            }

            Duration duration = Duration.between(from, to);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() - (hours * 60);

            double computerPrice = 0;
            if (night == Night.NIGHT) {
                computerPrice = (hours * 60 + minutes) * (computer.getNightPrice() / 60);
            } else {
                computerPrice = (hours * 60 + minutes) * (computer.getPrice() / 60);
            }

            totalPrice += computerPrice;

            User user = getPrinciple();
            if (user.isBlocked()) {
                throw new UserBlockedException("User is blocked and cannot book computer");
            }
            booking = new Booking();
            booking.setFroms(from);
            booking.setBto(to);
            booking.setShowDate(new Date());
            booking.setResponse("OK");
            booking.setTotalPrice(computerPrice);
            booking.setUser(user);
            booking.setComputer(computer);

            computer.setClubStatus(ClubStatus.BOOKED);

            Booking bookingSave = bookingRepository.save(booking);
            computerRepository.save(computer);
            orderHistoryService.createOrderHistory(bookingSave, LocalDateTime.now());

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    computer.setClubStatus(ClubStatus.NOT_BOOKED);
                    computerRepository.save(computer);
                }
            }, Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setMessage("Booking successful for " + computers.size() + " computers");
        bookingResponse.setFrom(from);
        bookingResponse.setTo(to);
        bookingResponse.setTotalPrice(totalPrice);

        if (booking != null) {
            bookingResponse.setId(booking.getId());
            bookingResponse.setUserId(booking.getUser().getId());
        }

        return bookingResponse;
    }

    public User getPrinciple() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    try {
                        throw new NotFoundException(String.format("Пользователь с таким электронным адресом: %s не найден!", email));
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}

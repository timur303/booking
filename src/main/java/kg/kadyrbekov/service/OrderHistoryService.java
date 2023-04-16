package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.OrderHistoryResponse;
import kg.kadyrbekov.exception.UnauthorizedAccessException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.model.entity.OrderHistory;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.OrderHistoryRepository;
import kg.kadyrbekov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository, UserRepository userRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.userRepository = userRepository;
    }

    public User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public OrderHistoryResponse createOrderHistory(Booking booking, LocalDateTime bookingTime) {
        User user = getAuthentication();
        OrderHistory orderHistory = new OrderHistory();
        OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();
        orderHistoryResponse.setId(orderHistory.getId());
        orderHistoryResponse.setBookingId(booking.getId());
        orderHistoryResponse.setUserId(user.getId());
        orderHistoryResponse.setPrice(booking.getTotalPrice());
//        orderHistoryResponse.setCabinOrVolleyballPrice(booking.getCabin() != null ? booking.getCabin().getPrice() : booking.getVolleyball().getPrice());
        orderHistory.setBooking(booking);
        orderHistory.setUser(user);
        orderHistory.setBookingTime(bookingTime);
        orderHistoryRepository.save(orderHistory);

        return orderHistoryResponse;
    }


    public OrderHistoryResponse getHistoryById(Long id) throws UnauthorizedAccessException {
        User authenticatedUser = getAuthentication();

        OrderHistory orderHistory = orderHistoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order history with ID " + id + " not found"));

        if (!isAdmin(authenticatedUser) && !orderHistory.getUser().equals(authenticatedUser)) {
            throw new UnauthorizedAccessException("You are not authorized to access this order history");
        }

        OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();
        orderHistoryResponse.setId(orderHistory.getId());
        orderHistoryResponse.setBookingId(orderHistory.getBooking().getId());
        orderHistoryResponse.setUserId(orderHistory.getUser().getId());
        orderHistoryResponse.setPrice(orderHistory.getBooking().getTotalPrice());

        return orderHistoryResponse;
    }

    public List<OrderHistoryResponse> getAllOrderHistories() {
        User authenticatedUser = getAuthentication(); // Assuming you have a method to retrieve the authenticated user

        List<OrderHistory> orderHistories;
        if (isAdmin(authenticatedUser)) {
            orderHistories = orderHistoryRepository.findAll();
        } else {
            orderHistories = orderHistoryRepository.findByUser(authenticatedUser);
        }

        List<OrderHistoryResponse> orderHistoryResponses = new ArrayList<>();

        for (OrderHistory orderHistory : orderHistories) {
            OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();
            orderHistoryResponse.setId(orderHistory.getId());
            orderHistoryResponse.setUserId(orderHistory.getUser().getId());

            Booking booking = orderHistory.getBooking();
            if (booking != null) {
                orderHistoryResponse.setBookingId(booking.getId());
                orderHistoryResponse.setPrice(booking.getTotalPrice());
            }

            orderHistoryResponses.add(orderHistoryResponse);
        }

        return orderHistoryResponses;
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }


}

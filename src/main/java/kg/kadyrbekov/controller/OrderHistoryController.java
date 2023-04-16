package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.OrderHistoryResponse;
import kg.kadyrbekov.exception.UnauthorizedAccessException;
import kg.kadyrbekov.service.OrderHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/orderHistory")
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;

    public OrderHistoryController(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderHistoryResponse> getOrderHistoryById(@PathVariable("id") Long id) {
        try {
            OrderHistoryResponse orderHistoryResponse = orderHistoryService.getHistoryById(id);
            return ResponseEntity.ok(orderHistoryResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderHistoryResponse>> getAllOrderHistories() {
        List<OrderHistoryResponse> orderHistoryResponses = orderHistoryService.getAllOrderHistories();
        return ResponseEntity.ok(orderHistoryResponses);
    }

}


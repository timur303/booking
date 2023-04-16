package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.BookingRequest;
import kg.kadyrbekov.dto.BookingResponse;
import kg.kadyrbekov.exception.TimeExpiredException;
import kg.kadyrbekov.exception.TurfAlreadyBookedException;
import kg.kadyrbekov.exception.UserBlockedException;
import kg.kadyrbekov.model.entity.Booking;
import kg.kadyrbekov.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Booking")
@RequestMapping("api/book")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/cabins/{cabinIds}")
    public ResponseEntity<BookingResponse> bookCabins(@RequestBody BookingRequest bookingRequest, @PathVariable List<Long> cabinIds) throws UserBlockedException {
        try {
            BookingResponse bookingResponse = bookingService.bookCabins(bookingRequest, cabinIds);
            return ResponseEntity.ok().body(bookingResponse);
        } catch (TimeExpiredException | TurfAlreadyBookedException e) {
            return ResponseEntity.badRequest().body(new BookingResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookingResponse("An error occurred while booking the cabins."));
        }
    }

    @PostMapping("/volleyballs/{volleyballIds}")
    public ResponseEntity<BookingResponse> bookVolleyballs(@RequestBody BookingRequest bookingRequest, @PathVariable List<Long> volleyballIds) throws UserBlockedException {
        try {
            BookingResponse bookingResponse = bookingService.bookVolleyballs(bookingRequest, volleyballIds);
            return ResponseEntity.ok().body(bookingResponse);
        } catch (TimeExpiredException | TurfAlreadyBookedException e) {
            return ResponseEntity.badRequest().body(new BookingResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookingResponse("An error occurred while booking the volleyballs."));
        }
    }

    @GetMapping("/{id}")
    public Booking get(@PathVariable Long id) {
        return bookingService.getByID(id);
    }

    @PostMapping("/turfs/{turfIds}")
    public ResponseEntity<BookingResponse> bookTurfs(@RequestBody BookingRequest bookingRequest, @PathVariable List<Long> turfIds) throws UserBlockedException {
        try {
            BookingResponse bookingResponse = bookingService.bookTurfs(bookingRequest, turfIds);
            return ResponseEntity.ok().body(bookingResponse);
        } catch (TimeExpiredException | TurfAlreadyBookedException e) {
            return ResponseEntity.badRequest().body(new BookingResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookingResponse("An error occurred while booking the turfs."));
        }
    }

    @PostMapping("/computers/{compIds}")
    public ResponseEntity<BookingResponse> bookComps(@RequestBody BookingRequest bookingRequest, @PathVariable List<Long> compIds) throws UserBlockedException {
        try {
            BookingResponse bookingResponse = bookingService.bookComputers(bookingRequest, compIds);
            return ResponseEntity.ok().body(bookingResponse);
        } catch (TimeExpiredException | TurfAlreadyBookedException e) {
            return ResponseEntity.badRequest().body(new BookingResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BookingResponse("An error occurred while booking the comps."));
        }
    }
}

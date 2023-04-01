package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ReviewRequest;
import kg.kadyrbekov.dto.ReviewResponse;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.service.ClubService;
import kg.kadyrbekov.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewsController {

    private final ClubService clubService;
    private final ReviewService reviewService;

    @PostMapping("club/{id}")
    public ReviewResponse save(@RequestBody ReviewRequest request, @PathVariable Long id) {
        return reviewService.create(request, id);
    }

}

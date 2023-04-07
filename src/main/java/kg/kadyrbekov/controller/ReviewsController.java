package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ReviewRequest;
import kg.kadyrbekov.dto.ReviewResponse;
import kg.kadyrbekov.service.ClubService;
import kg.kadyrbekov.service.ReviewService;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("club/{id}")
    public ReviewResponse update(@RequestBody ReviewRequest request, @PathVariable Long id) {
        return reviewService.update(request, id);
    }

    @DeleteMapping("club/{id}")
    public void deleteById(@PathVariable Long id) {
        reviewService.deleteById(id);
    }
}

package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.ReviewRequest;
import kg.kadyrbekov.dto.ReviewResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "Reviews")
@RequestMapping("api/review")
public class ReviewsController {

    private final ReviewService reviewService;

    @PostMapping("clubWrite/{id}")
    public ReviewResponse save(@RequestBody ReviewRequest request, @PathVariable Long id) throws NotFoundException {
        return reviewService.create(request, id);
    }

    @PatchMapping("clubWUpdate/{id}")
    public ReviewResponse update(@RequestBody ReviewRequest request, @PathVariable Long id) throws NotFoundException {
        return reviewService.update(request, id);
    }

    @DeleteMapping("clubWDelete/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        reviewService.deleteById(id);
    }

    @PostMapping("complexWrite/{id}")
    public ReviewResponse saveComplex(@RequestBody ReviewRequest request, @PathVariable Long id) throws NotFoundException {
        return reviewService.createComplex(request, id);
    }

    @PatchMapping("complexWUpdate/{id}")
    public ReviewResponse updateComplex(@RequestBody ReviewRequest request, @PathVariable Long id) throws NotFoundException {
        return reviewService.updateComplex(request, id);
    }

    @DeleteMapping("complexWDelete/{id}")
    public void deleteByIdComplex(@PathVariable Long id) throws NotFoundException {
        reviewService.deleteByIdComplex(id);
    }
}

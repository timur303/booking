package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/club")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ClubResponse save(@RequestBody ClubRequest clubRequest) {
        return clubService.create(clubRequest);
    }

    //    @PostMapping("review")
//    public void saveClubWithReviews(@RequestBody Club club, @RequestBody List<Review> reviews) {
//        clubService.saveClubWithReviews(club, reviews);
//    }
    @PatchMapping("/{id}")
    public ClubResponse update(@RequestBody ClubRequest request, @PathVariable Long id) {
        return clubService.update(request, id);
    }

    @GetMapping("/{id}")
    public Club getById(@PathVariable Long id) {
        return clubService.findByIdClub(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        clubService.deleteById(id);
    }

}

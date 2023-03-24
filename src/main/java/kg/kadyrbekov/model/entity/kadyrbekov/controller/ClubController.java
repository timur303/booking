package kg.kadyrbekov.model.entity.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.model.entity.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.model.entity.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.kadyrbekov.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/club")
public class ClubController {

    private final ClubService clubService;


    @PostMapping
    public ClubResponse save(@RequestBody ClubRequest clubRequest) {
        return clubService.create(clubRequest, clubRequest.getUserId());
    }

    @PatchMapping("/{id}")
    public ClubResponse update(@RequestBody ClubRequest request, @PathVariable Long id) {
        return clubService.update(request,id);
    }

    @GetMapping("/{id}")
    public Club getById(@PathVariable Long id) {
        return clubService.findByIdClub(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        clubService.deleteById(id);
    }

}

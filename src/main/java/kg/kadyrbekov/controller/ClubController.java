package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.entity.Club;
import kg.kadyrbekov.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/club")
public class ClubController {

    private final ClubService clubService;


    @PostMapping
    public ClubResponse save(@RequestBody ClubRequest clubRequest) {
      return clubService.create(clubRequest,clubRequest.getUserId());
    }

    @GetMapping("/{id}")
    public ClubResponse getById(@PathVariable Long id){
        return clubService.findByIdClub(id);
    }

}

package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.entity.Club;
import kg.kadyrbekov.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/club")
public class ClubController {

    private final ClubService clubService;


    @PostMapping("/save")
    public Club save(@RequestBody ClubRequest clubRequest) {
      return clubService.create(clubRequest,clubRequest.getUserId());
    }
}

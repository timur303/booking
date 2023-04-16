package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/club")
@Api(tags = "Club")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/saveClub")
    public ClubResponse createClub(@RequestBody ClubRequest request) throws NotFoundException {
        return clubService.create(request);
    }

    @ApiOperation(value = "Update a club")
    @PatchMapping("/updateClub/{id}")
    public ClubResponse updateClub(@RequestBody ClubRequest request, @PathVariable Long id) throws NotFoundException {
        return clubService.update(request, id);
    }
    @GetMapping("/getClub/{id}")
    public Club getByIdClub(@PathVariable Long id) throws NotFoundException {
        return clubService.findByIdClub(id);
    }

    @ApiOperation(value = "Delete a club by Id")
    @DeleteMapping("/deleteClub/{id}")
    public void deleteByIdClub(@PathVariable Long id) throws NotFoundException {
        clubService.deleteById(id);
    }
}

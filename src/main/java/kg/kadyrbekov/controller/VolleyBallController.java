package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.VolleyballRequest;
import kg.kadyrbekov.dto.VolleyballResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.Volleyball;
import kg.kadyrbekov.service.VolleyballService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/volleyball")
@Api(tags = "Volleyballs")
@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
public class VolleyBallController {

    private final VolleyballService volleyballService;

    @PostMapping("/createVolleyball")
    public VolleyballResponse create(@RequestBody VolleyballRequest request) throws NotFoundException {
        try {
            return volleyballService.create(request);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateVolleyball/{id}")
    public VolleyballResponse update(@RequestBody VolleyballRequest request, @PathVariable Long id) throws NotFoundException {
        return volleyballService.update(request, id);
    }

    @DeleteMapping("/deleteVolleyball/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        volleyballService.deleteById(id);
    }

    @GetMapping("/getVolleyball/{id}")
    public Volleyball getById(@PathVariable Long id) throws NotFoundException {
        return volleyballService.findByIdVolleyball(id);
    }
}

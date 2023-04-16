package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.TurfRequest;
import kg.kadyrbekov.dto.TurfResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.Turf;
import kg.kadyrbekov.service.TurfService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/turf")
@Api(tags = "Turfs")
@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
public class TurfController {

    private final TurfService turfService;


    @PostMapping("/saveTurf")
    public TurfResponse create(@RequestBody TurfRequest request) throws NotFoundException {
        try {
            return turfService.create(request);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateTurf/{id}")
    public TurfResponse update(@RequestBody TurfRequest request,@PathVariable Long id) throws NotFoundException {
        return turfService.update(request, id);
    }

    @DeleteMapping("/deleteTurf/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        turfService.deleteById(id);
    }

    @GetMapping("/getTurf/{id}")
    public Turf getById(@PathVariable Long id) throws NotFoundException {
        return turfService.findByIdTurf(id);
    }
}

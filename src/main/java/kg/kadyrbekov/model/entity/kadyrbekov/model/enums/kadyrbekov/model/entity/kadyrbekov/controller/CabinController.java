package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.service.CabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cabin")
public class CabinController {

    private final CabinService cabinService;

    @PostMapping
    public CabinResponse create(@RequestBody CabinRequest request) {
        return cabinService.create(request);
    }

    @PatchMapping("/{id}")
    public CabinResponse update(@RequestBody CabinRequest request, @PathVariable Long id) {
        return cabinService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        cabinService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Cabin getById(@PathVariable Long id) {
        return cabinService.findByIdCabin(id);
    }

}

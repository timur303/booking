package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.dto.ComputerRequest;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.dto.ComputerResponse;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/computer")
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    public ComputerResponse save(@RequestBody ComputerRequest request) {
        return computerService.create(request);
    }

    @PatchMapping("/{id}")
    public ComputerResponse update(@RequestBody ComputerRequest request, @PathVariable Long id) {
        return computerService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        computerService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Computer getById(@PathVariable Long id) {
        return computerService.findByIdComputer(id);
    }
}

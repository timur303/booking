package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.ComputerRequest;
import kg.kadyrbekov.dto.ComputerResponse;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/computer")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    public ComputerResponse save(@RequestBody ComputerRequest request) {
        return computerService.create(request);
    }

    @PatchMapping("/{id}")
    public ComputerResponse update(@RequestBody ComputerRequest request,@PathVariable Long id) {
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

package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.kadyrbekov.dto.ComputerRequest;
import kg.kadyrbekov.dto.ComputerResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/computer")
@RequiredArgsConstructor
@Api(tags = "Computer")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping("/saveComputer")
    public ComputerResponse createComputer(@RequestBody ComputerRequest request) throws NotFoundException {
        return computerService.create(request);
    }

    @PatchMapping("/update/{id}")
    @ApiOperation(value = "update computer")
    public ComputerResponse updateComputer(@RequestBody ComputerRequest request,@PathVariable Long id) throws NotFoundException {
        return computerService.update(request, id);
    }

    @DeleteMapping("/deleteComp/{id}")
    @ApiOperation(value = "delete computer")
    public void deleteByIdComputer(@PathVariable Long id) throws NotFoundException {
        computerService.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Computer getByIdComputer(@PathVariable Long id) throws NotFoundException {
        return computerService.findByIdComputer(id);
    }
}


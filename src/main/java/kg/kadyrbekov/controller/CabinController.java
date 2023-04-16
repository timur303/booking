package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.service.CabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cabin")
@Api(tags = "Cabin")
@PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER')")
public class CabinController {

    private final CabinService cabinService;


    @PostMapping("/saveCabin")
    @ApiOperation(value = "Create a cabin")
    public CabinResponse createCabin(@RequestBody CabinRequest cabinRequest) throws NotFoundException {
        return cabinService.create(cabinRequest);
    }

    @ApiOperation(value = "Update a cabin")
    @PatchMapping("/updateCabin/{id}")
    public CabinResponse updateCabin(@RequestBody CabinRequest request, @PathVariable Long id) throws NotFoundException {
        return cabinService.update(request, id);
    }

    @GetMapping("/getCabin/{id}")
    @ApiOperation(value = "Get a cabin by id")
    public Cabin getByIdCabin(@PathVariable Long id) throws NotFoundException {
        return cabinService.findByIdCabin(id);
    }

    @ApiOperation(value = "Delete a cabin by Id")
    @DeleteMapping("/deleteCabin/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        cabinService.deleteByIdCabin(id);
    }
}

package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.SportComplexRequest;
import kg.kadyrbekov.dto.SportComplexResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.entity.SportComplex;
import kg.kadyrbekov.service.SportComplexService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/complex")
@Api(tags="Sport Complex")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ComplexController {

    private final SportComplexService complexService;

    @PostMapping("/saveComplex")
    public SportComplexResponse create(@RequestBody SportComplexRequest request) throws NotFoundException {
        return complexService.create(request);
    }

    @PatchMapping("/updateComplex/{id}")
    public SportComplexResponse update(@RequestBody SportComplexRequest request, @PathVariable Long id) throws NotFoundException {
        return complexService.update(request, id);
    }

    @DeleteMapping("/deleteComplex/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException {
        complexService.deleteById(id);
    }

    @GetMapping("/getComplex/{id}")
    public SportComplex getById(@PathVariable Long id) throws NotFoundException {
        return complexService.findByIdComplex(id);

    }
}

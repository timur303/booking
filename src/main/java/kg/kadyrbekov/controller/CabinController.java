package kg.kadyrbekov.controller;

import kg.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.service.CabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cabin")
public class CabinController {

    private final CabinService cabinService;

    @PostMapping
    public CabinResponse create(@RequestBody CabinRequest request) {
        return cabinService.create(request);
    }
}

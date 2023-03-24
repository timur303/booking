package kg.kadyrbekov.controller;

import kg.kadyrbekov.service.CabinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cabin")
public class CabinController {

    private final CabinService cabinService;
}

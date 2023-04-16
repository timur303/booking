package kg.kadyrbekov.controller;

import io.swagger.annotations.Api;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
@Api(tags = "Managers")
@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
public class ManagerController {

    private final ManagerService managerService;

//    @PostMapping("/{userId}/block")
//    public String blockUser(@PathVariable Long userId) throws NotFoundException {
//        managerService.blockUser(userId);
//        return "Successful blocked " + userId;
//    }

//    @PostMapping("/{userId}/unblock")
//    public String unblockUser(@PathVariable Long userId) throws NotFoundException {
//        managerService.unblockUser(userId);
//        return "Successful unblocked " + userId;
//    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return managerService.getUserById(userId);
    }

}


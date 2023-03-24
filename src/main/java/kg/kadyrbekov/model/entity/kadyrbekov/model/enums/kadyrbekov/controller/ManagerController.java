package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.controller;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/{userId}/block")
    public String blockUser(@PathVariable Long userId) {
        managerService.blockUser(userId);
        return "Successful blocked " + userId;
    }

    @PostMapping("/{userId}/unblock")
    public String unblockUser(@PathVariable Long userId) {
        managerService.unblockUser(userId);
        return "Successful unblocked " + userId;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return managerService.getUserById(userId);
    }

}


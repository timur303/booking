package kg.kadyrbekov.controller;


import kg.kadyrbekov.model.User;
import kg.kadyrbekov.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/{userId}/block")
    public String blockUser(@PathVariable Long userId) {
        adminService.blockUser(userId);
        return "Successful blocked " + userId;
    }

    @PostMapping("/{userId}/unblock")
    public String unblockUser(@PathVariable Long userId) {
        adminService.unblockUser(userId);
        return "Successful unblocked " + userId;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return adminService.getUserById(userId);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return "Successful removed " + id;
    }


}



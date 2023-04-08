package kg.kadyrbekov.controller;


import kg.kadyrbekov.model.User;
import kg.kadyrbekov.service.AdminService;
import kg.kadyrbekov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {


    private final UserService userService;
    private final AdminService adminService;

    @PostMapping("/{email}/roles/manager")
    public ResponseEntity<Void> assignManagerRole(@PathVariable("email") String userEmail) {
        adminService.givesRoles(userEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public String listUsers(Model model) {
        List<User> users = adminService.getAll();
        model.addAttribute("users", users);
        return "login";
    }

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



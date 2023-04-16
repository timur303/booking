package kg.kadyrbekov.controller;


import io.swagger.annotations.Api;
import kg.kadyrbekov.dto.UserRequest;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Api(tags = "Admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/{email}/roles/manager")
    public ResponseEntity<Void> assignManagerRole(@PathVariable("email") String userEmail, @RequestBody UserRequest request) throws NotFoundException {
        adminService.givesRoles(userEmail, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public String listUsers(Model model) {
        List<User> users = adminService.getAll();
        model.addAttribute("users", users);
        return "login";
    }

    @PostMapping("/{userId}/block")
    public String blockUser(@PathVariable Long userId) throws NotFoundException {
        adminService.blockUser(userId);
        return "Successful blocked " + userId;
    }

    @PostMapping("/{userId}/unblock")
    public String unblockUser(@PathVariable Long userId) throws NotFoundException {
        adminService.unblockUser(userId);
        return "Successful unblocked " + userId;
    }

    @GetMapping("/get/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return adminService.getUserById(userId);
    }


    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return "Successful removed " + id;
    }

    @GetMapping("admin")
    public String get(Model model){
        model.addAttribute("admin", new User());
        return "admin";
    }
}



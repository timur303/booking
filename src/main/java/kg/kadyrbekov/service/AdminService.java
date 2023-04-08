package kg.kadyrbekov.service;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final UserService userService;

    public String blockUser(Long userId) {
        User user = findByUserId(userId);
        user.setBlocked(true);
        userRepository.save(user);
        return "Successful user blocked " + userId;
    }

    public void unblockUser(Long userId) {
        User user = findByUserId(userId);
        user.setBlocked(false);
        userRepository.save(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void givesRoles(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user != null) {
            user.setRole(Role.MANAGER);
            userRepository.save(user);
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found ", userId)));
    }

}

package kg.kadyrbekov.service;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepository;

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

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found ", userId)));
    }

}
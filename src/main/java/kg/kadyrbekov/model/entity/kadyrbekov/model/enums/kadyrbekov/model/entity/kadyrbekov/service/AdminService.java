package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.service;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AdminService {

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

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found ", userId)));
    }

}

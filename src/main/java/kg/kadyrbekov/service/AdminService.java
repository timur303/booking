package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.UserRequest;
import kg.kadyrbekov.dto.UserResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;


    public String blockUser(Long userId) throws NotFoundException {
        User user = findByUserId(userId);
        user.setBlocked(true);
        userRepository.save(user);
        return "Successful user blocked " + userId;
    }

    public void unblockUser(Long userId) throws NotFoundException {
        User user = findByUserId(userId);
        user.setBlocked(false);
        userRepository.save(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

//    public void givesRoles(String email, UserRequest request) {
//        User user = userRepository.findByEmail(email).get();
//        Optional<User> existingUserWithManagerId = userRepository.findByManagerId(request.getManagerId());
//        if (existingUserWithManagerId.isPresent() && !existingUserWithManagerId.get().getId().equals(user.getId())) {
//            throw new RuntimeException("The managerId is already assigned to another user");
//        }
//        if (user != null) {
//            user.setRole(Role.MANAGER);
//            user.setManagerId(request.getManagerId());
//            userRepository.save(user);
//        }
//    }

    public UserResponse createUser(UserRequest userRequest, Role role) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setAge(userRequest.getAge());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(userRequest.getPassword());
        user.setBlocked(false);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .age(savedUser.getAge())
                .build();
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public User findByUserId(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found ", userId)));
    }

}

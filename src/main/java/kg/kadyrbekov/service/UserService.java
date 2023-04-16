package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.UserRequest;
import kg.kadyrbekov.dto.UserResponse;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserResponse register(UserRequest userRequest) {
        User user1 = new User();
        userRepository.findByEmail(user1.getEmail());
        User user = mapToEntity(userRequest);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return mapToResponse(user);
    }

    public User mapToEntity(UserRequest request) {
        String email = request.getEmail();

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(email);
        user.setAge(request.getAge());
        user.setPassword(request.getPassword());
        user.setManagerId(request.getManagerId());

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email address already registered.");
        }

        if (email.equals("admin@gmail.com")) {
            if (isAdminAlreadyLoggedIn()) {
                throw new RuntimeException("Admin is already logged in");
            } else {
                user.setRole(Role.ADMIN);
                setAdminLoggedIn(true);
            }
        } else {
            user.setRole(Role.USER);
        }

        return user;
    }

    public UserResponse updateProfile(Long userId, UserRequest updatedUserRequest) {
        User existingUser = userRepository.findById(userId).get();

        if (existingUser == null) {
            throw new RuntimeException("User not found.");
        }

        existingUser.setFirstName(updatedUserRequest.getFirstName());
        existingUser.setLastName(updatedUserRequest.getLastName());
        existingUser.setEmail(updatedUserRequest.getEmail());
        existingUser.setAge(updatedUserRequest.getAge());

        User updatedUser = userRepository.save(existingUser);

        return mapToResponse(updatedUser);
    }


    private static boolean adminLoggedIn = false;

    private synchronized boolean isAdminAlreadyLoggedIn() {
        return adminLoggedIn;
    }

    private synchronized void setAdminLoggedIn(boolean loggedIn) {
        adminLoggedIn = loggedIn;
    }


    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .clubManagerId(user.getManagerId())
                .build();
    }

}

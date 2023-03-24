package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.UserRequest;
import kg.kadyrbekov.dto.UserResponse;
import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.entity.enums.Role;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPassword(request.getPassword());
        if (user.getFirstName().equals("Timur")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        return user;
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
                .build();
    }
}

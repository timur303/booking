package kg.kadyrbekov.controller;


import io.swagger.annotations.Api;
import kg.kadyrbekov.config.jwt.JwtTokenUtil;
import kg.kadyrbekov.dto.UserRequest;
import kg.kadyrbekov.dto.UserResponse;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.mapper.LoginMapper;
import kg.kadyrbekov.mapper.LoginResponse;
import kg.kadyrbekov.mapper.ValidationType;
import kg.kadyrbekov.repository.UserRepository;
import kg.kadyrbekov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "Authentication")
@RequestMapping("/api/jwt")
public class AuthController {

    private final LoginMapper loginMapper;

    private final JwtTokenUtil jwTokenUtil;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;


    @PostMapping("login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody UserRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginMapper.loginView(jwTokenUtil.generateToken(user), ValidationType.SUCCESSFUL, user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMapper.loginView("", ValidationType.LOGIN_FAILED, null));

        }
    }

    @PostMapping("/registration")
    public UserResponse create(@RequestBody UserRequest userRequest) {
        return userService.register(userRequest);
    }

    @PatchMapping("/update/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateProfile(id, request);
    }
}

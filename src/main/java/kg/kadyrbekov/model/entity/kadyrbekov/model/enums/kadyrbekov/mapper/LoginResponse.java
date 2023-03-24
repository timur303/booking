package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String jwtToken;

    private String message;

    private String authorities;

}

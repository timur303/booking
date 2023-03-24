package kg.kadyrbekov.model.entity.kadyrbekov.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    
    private Long id;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;

    private String password;

    private int age;

}

package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String firstName;

    private String lastName;

    private int age;

    private String email;

    private Long phoneNumber;

    private String password;

}

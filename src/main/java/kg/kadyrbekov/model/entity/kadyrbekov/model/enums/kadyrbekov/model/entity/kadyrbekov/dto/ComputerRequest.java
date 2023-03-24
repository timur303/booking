package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerRequest {

    private String name;

    private String image;

    private String description;

    private String price;

    private Long userId;

    private User user;

    private Long clubId;


}

package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.dto;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CabinRequest {

    private String name;

    private String image;

    private String description;

    private String price;

    private User user;

    private Long userId;

    private Long clubId;
}

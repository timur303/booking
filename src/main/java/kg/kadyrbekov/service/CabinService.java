package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.entity.Cabin;
import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.repository.CabinRepository;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabinService {

    private final CabinRepository cabinRepository;

    private final UserRepository userRepository;

    private final ClubRepository clubRepository;

    public Cabin create(CabinRequest cabinRequest) {
        User user = new User();
        cabinRequest.setUser(user);
        Cabin cabin = mapToEntity(cabinRequest);
        cabinRepository.save(cabin);
        return cabin;
    }


    public Cabin mapToEntity(CabinRequest request) {
        Optional<User> user = Optional.of(userRepository.findById(request.getUserId()).get());
        Cabin cabin = new Cabin();
        BeanUtils.copyProperties(request, user);
        cabin.setUserId(request.getUserId());
        cabin.setUser(user.get());
        cabin.setBooked(request.isBooked());
        cabin.setName(request.getName());
        cabin.setClubStatus(request.getClubStatus());

        return cabin;
    }

    public CabinResponse cabinResponse(Cabin cabin) {
        return CabinResponse.builder()
                .id(cabin.getId())
                .name(cabin.getName())
                .price(cabin.getPrice())
                .description(cabin.getDescription())
                .image(cabin.getImage())
                .clubStatus(cabin.getClubStatus())
                .isBooked(cabin.isBooked())
                .build();
    }
}

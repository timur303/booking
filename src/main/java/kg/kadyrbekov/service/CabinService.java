package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.repository.CabinRepository;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabinService {

    private final CabinRepository cabinRepository;

    private final UserRepository userRepository;

    private final ClubRepository clubRepository;

    public CabinResponse create(CabinRequest request) {
        User user = new User();
        Club club = findByIdClub(request.getClubId());
        Cabin cabin = mapToEntity(request);
        cabin.setClub(club);
        cabin.setClubId(request.getClubId());
        request.setUser(user);
        club.setCabins(cabin.getClub().getCabins());
        cabinRepository.save(cabin);

        return cabinResponse(cabin);
    }

    public CabinResponse update(CabinRequest request, Long id) {
        Cabin cabin = findByIdCabin(id);
        cabin.setName(request.getName());
        cabin.setDescription(request.getDescription());
        cabin.setPrice(request.getPrice());
        cabin.setClubId(request.getClubId());
        cabinRepository.save(cabin);
        return cabinResponse(cabin);
    }

    public void deleteById(Long id) {
        Cabin cabin = findByIdCabin(id);
        cabinRepository.delete(cabin);
    }

    public Club findByIdClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(
                () -> new NotFoundException(String.format("Club with id not found ", clubId)));
    }

    public Cabin findByIdCabin(Long cabinId) {
        return cabinRepository.findById(cabinId).orElseThrow(
                () -> new NotFoundException(String.format("Cabin with id not found ", cabinId)));
    }

    public Cabin mapToEntity(CabinRequest request) {
        Optional<User> user = Optional.of(userRepository.findById(request.getUserId()).
                orElseThrow(()->new NotFoundException("User with id not found")));
        Cabin cabin = new Cabin();
        cabin.setUser(user.get());
        cabin.setUserId(request.getUserId());
        cabin.setName(request.getName());
        cabin.setPrice(request.getPrice());
        cabin.setDescription(request.getDescription());
        cabin.setClubId(request.getClubId());
        cabin.setImage(request.getImage());
        cabin.setClubStatus(request.getClubStatus());

        return cabin;
    }

    public CabinResponse mapToResponse(Cabin cabin) {
        if (cabin == null) {
            return null;
        }
        CabinResponse cabinResponse = new CabinResponse();
        cabinResponse.setPrice(cabin.getPrice());
        cabinResponse.setId(cabin.getId());
        cabinResponse.setClubId(cabin.getClubId());
        cabinResponse.setImage(cabin.getImage());
        cabinResponse.setName(cabin.getName());
        cabinResponse.setDescription(cabin.getDescription());
        cabinResponse.setClubStatus(cabin.getClubStatus());

        return cabinResponse;
    }

    public CabinResponse cabinResponse(Cabin cabin) {
        return CabinResponse.builder()
                .id(cabin.getId())
                .userId(cabin.getUserId())
                .name(cabin.getName())
                .price(cabin.getPrice())
                .description(cabin.getDescription())
                .image(cabin.getImage())
                .clubStatus(cabin.getClubStatus())
                .clubId(cabin.getClubId())
                .clubStatus(cabin.getClubStatus())
                .build();
    }
}

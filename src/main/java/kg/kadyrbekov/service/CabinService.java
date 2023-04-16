package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.CabinRequest;
import kg.kadyrbekov.dto.CabinResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.CabinRepository;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CabinService {

    private final CabinRepository cabinRepository;

    private final UserRepository userRepository;

    private final ClubRepository clubRepository;

    public CabinResponse create(CabinRequest request) throws NotFoundException {
        User user = getAuthentication();
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(
                () -> new NotFoundException("Club with id not found"));
        Cabin cabin = mapToEntity(request);
        cabin.setUser(user);
        cabin.setClub(club);
        cabin.setClubId(request.getClubId());
        cabin.setUserId(user.getId());
        club.setCabins(cabin.getClub().getCabins());
        String trimmedName = request.getName().trim();
        if (cabinRepository.existsByClubAndName(club, trimmedName)) {
            throw new RuntimeException("Cabin with the given name already exists");
        }
        if (club.getManagerName().isEmpty()) {
            throw new RuntimeException("You can't add your cabin");
        }
        if (!user.getRole().equals(Role.ADMIN)) {
            if (!club.getClubManagerId().equals(user.getManagerId())) {
                throw new RuntimeException("You cannot add a cabin to another club");
            }
        }
        cabinRepository.save(cabin);

        return mapToResponse(cabin);
    }

    public User getAuthentication() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }

    public CabinResponse update(CabinRequest request, Long id) throws NotFoundException {
        Cabin cabin = findByIdCabin(id);
        cabin.setName(request.getName());
        cabin.setDescription(request.getDescription());
        cabin.setPrice(request.getPrice());
        cabin.setNightPrice(request.getNightPrice());
        cabin.setImage(request.getImage());
        cabinRepository.save(cabin);
        return cabinResponse(cabin);
    }


    public void deleteByIdCabin(Long id) throws NotFoundException {
        Cabin cabin = findByIdCabin(id);
        cabinRepository.delete(cabin);
    }


    public Cabin findByIdCabin(Long cabinId) throws NotFoundException {
        return cabinRepository.findById(cabinId).orElseThrow(
                () -> new NotFoundException(("Cabin with id not found")));
    }

    public Cabin mapToEntity(CabinRequest request) {
        Cabin cabin = new Cabin();
        cabin.setName(request.getName());
        cabin.setPrice(request.getPrice());
        cabin.setDescription(request.getDescription());
        cabin.setClubId(request.getClubId());
        cabin.setImage(request.getImage());
        cabin.setClubStatus(request.getClubStatus());
        cabin.setUser(request.getUser());
        cabin.setNightPrice(request.getNightPrice());

        return cabin;
    }

    public CabinResponse mapToResponse(Cabin cabin) {
        CabinResponse cabinResponse = new CabinResponse();
        cabinResponse.setId(cabin.getId());
        cabinResponse.setClubId(cabin.getClubId());
        cabinResponse.setPrice(cabin.getPrice());
        cabinResponse.setName(cabin.getName());
        cabinResponse.setImage(cabin.getImage());
        cabinResponse.setDescription(cabin.getDescription());
        cabinResponse.setClubStatus(cabin.getClubStatus());
        cabinResponse.setUserId(cabin.getUserId());
        cabinResponse.setNightPrice(cabin.getNightPrice());

        return cabinResponse;
    }

    public CabinResponse cabinResponse(Cabin cabin) {
        return CabinResponse.builder()
                .id(cabin.getId())
                .userId(cabin.getUserId())
                .price(cabin.getPrice())
                .name(cabin.getName())
                .description(cabin.getDescription())
                .image(cabin.getImage())
                .clubStatus(cabin.getClubStatus())
                .clubId(cabin.getClubId())
                .build();
    }
}

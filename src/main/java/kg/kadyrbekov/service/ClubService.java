package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final UserRepository userRepository;

    public ClubResponse create(ClubRequest request) {
        User user = getAuthentication();
        Club club = mapToEntity(request);
        club.setUser(user);
        clubRepository.save(club);
        return mapToResponse(club);
    }


    public ClubResponse update(ClubRequest request, Long id) {
        Club club = findByIdClub(id);
        club.setClubName(request.getClubName());
        club.setLogo(request.getLogo());
        club.setManagerName(request.getManagerName());
        club.setDescription(request.getDescription());
        club.setCity(request.getCity());
        club.setPhoneNumber(request.getPhoneNumber());
        clubRepository.save(club);
        return clubResponse(club);
    }

    public void deleteById(Long id) {
        Club club = findByIdClub(id);
        clubRepository.delete(club);
    }

    public Club findByIdClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(
                () -> new NotFoundException(String.format("Club with id not found ", clubId)));
    }

    public Club mapToEntity(ClubRequest request) {
        Club club = new Club();
        club.setUser(request.getUser());
        club.setClubName(request.getClubName());
        club.setCity(request.getCity());
        club.setLogo(request.getLogo());
        club.setDescription(request.getDescription());
        club.setPhoneNumber(request.getPhoneNumber());
        club.setManagerName(request.getManagerName());
        club.setHomeNumber(request.getHomeNumber());
        club.setStreet(request.getStreet());
        club.setState(request.getState());
        club.setReviews(request.getReviews());
        return club;
    }


    public User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }


    public ClubResponse mapToResponse(Club club) {

        if (club == null) {
            return null;
        }

        ClubResponse clubResponse = new ClubResponse();
        clubResponse.setId(club.getId());
        clubResponse.setClubName(club.getClubName());
        clubResponse.setCity(club.getCity());
        clubResponse.setLogo(club.getLogo());
        clubResponse.setState(club.getState());
        clubResponse.setReviews(club.getReviews());
        clubResponse.setStreet(club.getStreet());
        clubResponse.setHomeNumber(club.getHomeNumber());
        clubResponse.setDescription(club.getDescription());
        clubResponse.setManagerName(club.getManagerName());
        clubResponse.setPhoneNumber(club.getPhoneNumber());
        return clubResponse;
    }

    public User findByIdUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found", userId)));
    }


    public ClubResponse clubResponse(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .clubName(club.getClubName())
                .description(club.getDescription())
                .phoneNumber(club.getPhoneNumber())
                .city(club.getCity())
                .logo(club.getLogo())
                .homeNumber(club.getHomeNumber())
                .state(club.getState())
                .street(club.getStreet())
                .phoneNumber(club.getPhoneNumber())
                .managerName(club.getManagerName())
                .reviews(club.getReviews())
                .build();
    }
}

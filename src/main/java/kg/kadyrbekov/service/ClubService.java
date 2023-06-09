package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final UserRepository userRepository;

    public ClubResponse create(ClubRequest request) throws NotFoundException {
        User user = getAuthentication();
        Club existingClubWithManagerId = clubRepository.findByClubManagerId(request.getClubManagerId()).orElse(null);
        if (existingClubWithManagerId != null && !existingClubWithManagerId.getId().equals(user.getId())) {
            throw new RuntimeException("The clubManagerId is already assigned to another club");
        }
        Club club = mapToEntity(request);
        club.setUser(user);
        club.setUserId(user.getId());
        clubRepository.save(club);

        return mapToResponse(club);
    }


    public ClubResponse update(ClubRequest request, Long id) throws NotFoundException {
        Club club = findByIdClub(id);
        club.setClubName(request.getClubName());
        club.setLogo(request.getLogo());
        club.setManagerName(request.getManagerName());
        club.setDescription(request.getDescription());
        club.setCity(request.getCity());
        club.setPhoneNumber(request.getPhoneNumber());
        club.setClubManagerId(request.getClubManagerId());
        club.setPhotos(request.getPhotos());
        club.setStreet(request.getStreet());
        club.setState(request.getState());

        clubRepository.save(club);
        return clubResponse(club);
    }

    public void deleteById(Long id) throws NotFoundException {
        Club club = findByIdClub(id);
        clubRepository.delete(club);
    }

    public Club findByIdClub(Long clubId) throws NotFoundException {
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
        club.setClubManagerId(request.getClubManagerId());
        club.setPhotos(request.getPhotos());
        return club;
    }


    public User getAuthentication() throws NotFoundException {
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
        clubResponse.setStreet(club.getStreet());
        clubResponse.setHomeNumber(club.getHomeNumber());
        clubResponse.setDescription(club.getDescription());
        clubResponse.setManagerName(club.getManagerName());
        clubResponse.setPhoneNumber(club.getPhoneNumber());
        clubResponse.setUserId(club.getUserId());
        clubResponse.setClubManagerId(club.getClubManagerId());
        clubResponse.setReviews(club.getReviews());
        clubResponse.setPhotos(club.getPhotos());
        return clubResponse;
    }

    public User findByIdUser(Long userId) throws NotFoundException {
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
                .photos(club.getPhotos())
                .build();
    }
}

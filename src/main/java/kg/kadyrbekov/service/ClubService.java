package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ClubRequest;
import kg.kadyrbekov.dto.ClubResponse;
import kg.kadyrbekov.entity.Club;
import kg.kadyrbekov.entity.User;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final UserRepository userRepository;


    public Club create(ClubRequest clubRequest, Long userId) {
        User user = userRepository.findById(userId).get();
        clubRequest.setUser(user);
        Club club = mapToEntity(clubRequest);
        clubRepository.save(club);
        return club;
    }


    public Club findByIdClub(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(()
                -> new NotFoundException("With id club not found"));
    }




    public Club mapToEntity(ClubRequest request) {
        Optional<User> user = Optional.of(userRepository.findById(request.getUserId()).get());
        Club club = new Club();
        BeanUtils.copyProperties(request, user);
        club.setUserId(request.getUserId());
        club.setUser(user.get());
        club.setClubName(request.getClubName());
        club.setCity(request.getCity());
        club.setLogo(request.getLogo());
        club.setReview(request.getReview());
        club.setDescription(request.getDescription());
        club.setPhoneNumber(request.getPhoneNumber());
        club.setManagerName(request.getManagerName());
        return club;
    }

    public ClubResponse mapToResponse(Club club) {

        if (club == null) {
            return null;
        }

        ClubResponse clubResponse = new ClubResponse();

        clubResponse.setId(club.getId());
        clubResponse.setClubName(club.getClubName());
        clubResponse.setCity(club.getCity());
        clubResponse.setComment(club.getComment());
        clubResponse.setLogo(club.getLogo());
        clubResponse.setDescription(club.getDescription());
        clubResponse.setManagerName(club.getManagerName());
        clubResponse.setPhoneNumber(club.getPhoneNumber());
        clubResponse.setReview(club.getReview());

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
                .comment(club.getComment())
                .userId(club.getUser().getId())
                .phoneNumber(club.getPhoneNumber())
                .city(club.getCity())
                .logo(club.getLogo())
                .review(club.getReview())
                .phoneNumber(club.getPhoneNumber())
                .managerName(club.getManagerName())
                .build();
    }
}

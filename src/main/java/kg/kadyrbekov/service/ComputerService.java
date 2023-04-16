package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ComputerRequest;
import kg.kadyrbekov.dto.ComputerResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.ComputerRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ComputerService {

    private final UserRepository userRepository;

    private final ComputerRepository computerRepository;

    private final ClubRepository clubRepository;

    public ComputerResponse create(ComputerRequest request) throws NotFoundException {
        User user = getAuthentication();
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(
                () -> new NotFoundException("Club with id not found"));
        Computer computer = mapToEntity(request);
        computer.setClub(club);
        computer.setClubId(request.getClubId());
        computer.setUser(user);
        computer.setUserId(user.getId());
        club.setComputers(computer.getClub().getComputers());
        if (club.getManagerName().isEmpty()) {
            throw new RuntimeException("You can't add your computer");
        }
        if (!user.getRole().equals(Role.ADMIN)) {
            if (!club.getClubManagerId().equals(user.getManagerId())) {
                throw new RuntimeException("You cannot add a computer to another club");
            }
        }
        computerRepository.save(computer);
        return mapToResponse(computer);
    }

    public User getAuthentication() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }


    public ComputerResponse update(ComputerRequest request, Long id) throws NotFoundException {
        Computer computer = findByIdComputer(id);
        computer.setName(request.getName());
        computer.setDescription(request.getDescription());
        computer.setPrice(request.getPrice());
        computer.setImage(request.getImage());
        computerRepository.save(computer);
        return computerResponse(computer);
    }

    public Computer findByIdComputer(Long computerId) throws NotFoundException {
        return computerRepository.findById(computerId).orElseThrow(
                ()-> new NotFoundException("Computer with id not found "));
    }


    public void deleteById(Long id) throws NotFoundException {
        Computer computer = findByIdComputer(id);
        computerRepository.delete(computer);
    }

    public Computer mapToEntity(ComputerRequest request) {
        Computer computer = new Computer();
        computer.setName(request.getName());
        computer.setPrice(request.getPrice());
        computer.setClubStatus(request.getClubStatus());
        computer.setDescription(request.getDescription());
        computer.setClubId(request.getClubId());
        computer.setImage(request.getImage());
        computer.setUser(request.getUser());
        computer.setNightPrice(request.getNightPrice());

        return computer;
    }

    public ComputerResponse mapToResponse(Computer computer) {
        ComputerResponse response = new ComputerResponse();
        response.setId(computer.getId());
        response.setPrice(computer.getPrice());
        response.setClubId(computer.getClubId());
        response.setName(computer.getName());
        response.setDescription(computer.getDescription());
        response.setImage(computer.getImage());
        response.setClubStatus(computer.getClubStatus());
        response.setUserId(computer.getUserId());
        response.setNightPrice(computer.getNightPrice());

        return response;
    }

    public ComputerResponse computerResponse(Computer computer) {
        return ComputerResponse.builder()
                .id(computer.getId())
                .userId(computer.getUserId())
                .name(computer.getName())
                .price(computer.getPrice())
                .description(computer.getDescription())
                .clubId(computer.getClubId())
                .image(computer.getImage())
                .clubStatus(computer.getClubStatus())
                .build();
    }
}

package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ComputerRequest;
import kg.kadyrbekov.dto.ComputerResponse;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.Computer;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.ComputerRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComputerService {

    private final UserRepository userRepository;

    private final ComputerRepository computerRepository;

    private final ClubRepository clubRepository;

    public ComputerResponse create(ComputerRequest request) {
        User user = new User();
        Club club = clubRepository.findById(request.getClubId()).get();
        Computer computer = mapToEntity(request);
        computer.setClub(club);
        computer.setClubId(request.getClubId());
        request.setUser(user);
        club.setComputers(computer.getClub().getComputers());
        computerRepository.save(computer);

        return computerResponse(computer);
    }


    public ComputerResponse update(ComputerRequest request, Long id) {
        Computer computer = findByIdComputer(id);
        computer.setName(request.getName());
        computer.setDescription(request.getDescription());
        computer.setPrice(request.getPrice());
        computer.setImage(request.getImage());
        computerRepository.save(computer);
        return computerResponse(computer);
    }

    public Computer findByIdComputer(Long computerId) {
        return computerRepository.findById(computerId).orElseThrow
                (() -> new NotFoundException(String.format("Computer with id not found", computerId)));
    }

    public void deleteById(Long id) {
        Computer computer = findByIdComputer(id);
        computerRepository.delete(computer);
    }

    public Computer mapToEntity(ComputerRequest request) {
        Optional<User> user = Optional.of(userRepository.findById(request.getUserId()).get());
        Computer computer = new Computer();
        computer.setUser(user.get());
        computer.setUserId(request.getUserId());
        computer.setName(request.getName());
        computer.setPrice(request.getPrice());
        computer.setDescription(request.getDescription());
        computer.setClubId(request.getClubId());
        computer.setImage(request.getImage());

        return computer;
    }

    public ComputerResponse mapToResponse(Computer computer) {
        if (computer == null) {
            return null;
        }
        ComputerResponse response = new ComputerResponse();
        response.setPrice(computer.getPrice());
        response.setId(computer.getId());
        response.setClubId(computer.getClubId());
        response.setImage(computer.getImage());

        response.setName(computer.getName());
        response.setDescription(computer.getDescription());
        response.setClubStatus(computer.getClubStatus());

        return response;
    }

    public ComputerResponse computerResponse(Computer computer) {
        return ComputerResponse.builder()
                .id(computer.getId())
                .userId(computer.getUserId())
                .name(computer.getName())
                .price(computer.getPrice())
                .description(computer.getDescription())
                .image(computer.getImage())
                .clubStatus(computer.getClubStatus())
                .clubId(computer.getClubId())
                .build();
    }
}

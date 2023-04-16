package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.VolleyballRequest;
import kg.kadyrbekov.dto.VolleyballResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.SportComplex;
import kg.kadyrbekov.model.entity.Volleyball;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.SportComplexRepository;
import kg.kadyrbekov.repository.UserRepository;
import kg.kadyrbekov.repository.VolleyballRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolleyballService {

    private final UserRepository userRepository;

    private final SportComplexRepository complexRepository;

    private final VolleyballRepository volleyballRepository;

    public SportComplex findByIdComplex(Long id) throws NotFoundException {
        return complexRepository.findById(id).orElseThrow(() -> new NotFoundException("Complex with id not found"));
    }

    public VolleyballResponse create(VolleyballRequest request) throws NotFoundException {
        User user = getAuthentication();
        SportComplex complex = findByIdComplex(request.getComplexId());
        Volleyball volleyball = mapToEntity(request);
        volleyball.setSportComplex(complex);
        volleyball.setSportComplexId(request.getComplexId());
        volleyball.setUser(user);
        volleyball.setUserId(user.getId());
        complex.setTurfs(volleyball.getSportComplex().getTurfs());
        if (complex.getManagerName().isEmpty()) {
            throw new RuntimeException("You can't add your volleyballHall");
        }
        if (!user.getRole().equals(Role.ADMIN)) {
            if (!complex.getComplexManagerId().equals(user.getManagerId())) {
                throw new RuntimeException("You cannot add a volleyball to another club");
            }
        }
        volleyballRepository.save(volleyball);

        return mapToResponse(volleyball);
    }

    public User getAuthentication() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }

    public Volleyball findByIdVolleyball(Long id) throws NotFoundException {
        return volleyballRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Volleyball by id not found "+ id));
    }

    public Volleyball mapToEntity(VolleyballRequest request) {
        Volleyball volleyball = new Volleyball();
        volleyball.setUser(request.getUser());
        volleyball.setDescription(request.getDescription());
        volleyball.setName(request.getName());
        volleyball.setImage(request.getImage());
        volleyball.setNightPrice(request.getNightPrice());
        volleyball.setPrice(request.getPrice());
        volleyball.setSportComplexId(request.getComplexId());
        volleyball.setClubStatus(request.getClubStatus());
        return volleyball;
    }
    public VolleyballResponse update(VolleyballRequest request, Long id) throws NotFoundException {
        Volleyball volleyball = findByIdVolleyball(id);
        volleyball.setName(request.getName());
        volleyball.setClubStatus(request.getClubStatus());
        volleyball.setPrice(request.getPrice());
        volleyball.setImage(request.getImage());
        volleyball.setNightPrice(request.getNightPrice());
        volleyballRepository.save(volleyball);

        return volleyballResponse(volleyball);
    }

    public void deleteById(Long id) throws NotFoundException {
        Volleyball volleyball = findByIdVolleyball(id);
        volleyballRepository.delete(volleyball);
    }

    public VolleyballResponse mapToResponse(Volleyball volleyball) {
        if (volleyball == null) {
            return null;
        }
        VolleyballResponse response = new VolleyballResponse();
        response.setPrice(volleyball.getPrice());
        response.setId(volleyball.getId());
        response.setComplexId(volleyball.getSportComplexId());
        response.setImage(volleyball.getImage());
        response.setName(volleyball.getName());
        response.setDescription(volleyball.getDescription());
        response.setClubStatus(volleyball.getClubStatus());
        response.setUserId(volleyball.getUserId());
        response.setNightPrice(volleyball.getNightPrice());
        return response;
    }
    public VolleyballResponse volleyballResponse(Volleyball volleyball) {
        return VolleyballResponse.builder().
                id(volleyball.getUserId()).
                name(volleyball.getName())
                .image(volleyball.getImage())
                .clubStatus(volleyball.getClubStatus())
                .price(volleyball.getPrice())
                .description(volleyball.getDescription())
                .userId(volleyball.getUserId())
                .nightPrice(volleyball.getNightPrice())
                .userId(volleyball.getUserId())
                .build();
    }
}

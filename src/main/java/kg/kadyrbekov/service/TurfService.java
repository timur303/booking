package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.TurfRequest;
import kg.kadyrbekov.dto.TurfResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.SportComplex;
import kg.kadyrbekov.model.entity.Turf;
import kg.kadyrbekov.model.enums.Role;
import kg.kadyrbekov.repository.SportComplexRepository;
import kg.kadyrbekov.repository.TurfRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TurfService {

    private final UserRepository userRepository;

    private final SportComplexRepository complexRepository;

    private final TurfRepository turfRepository;

    public SportComplex findByIdComplex(Long id) throws NotFoundException {
        return complexRepository.findById(id).orElseThrow(() -> new NotFoundException("Complex with id not found"));
    }

    public Turf findByIdTurf(Long id) throws NotFoundException {
        return turfRepository.findById(id).orElseThrow(() -> new NotFoundException("Turf by id not found" + id));
    }

    public TurfResponse create(TurfRequest request) throws NotFoundException {
        User user = getAuthentication();
        SportComplex complex = findByIdComplex(request.getComplexId());
        Turf turf = mapToEntity(request);
        turf.setSportComplex(complex);
        turf.setSportComplexId(request.getComplexId());
        turf.setUser(user);
        turf.setUserId(user.getId());
        complex.setTurfs(turf.getSportComplex().getTurfs());
        if (complex.getManagerName().isEmpty()) {
            throw new RuntimeException("You can't add your turf");
        }
        if (!user.getRole().equals(Role.ADMIN)) {
            if (!complex.getComplexManagerId().equals(user.getManagerId())) {
                throw new RuntimeException("You cannot add a turf to another club");
            }
        }
        turfRepository.save(turf);

        return mapToResponse(turf);
    }
    public TurfResponse update(TurfRequest request, Long id) throws NotFoundException {
        Turf turf = findByIdTurf(id);
        turf.setName(request.getName());
        turf.setClubStatus(request.getClubStatus());
        turf.setPrice(request.getPrice());
        turf.setImage(request.getImage());
        turf.setNightPrice(request.getNightPrice());
        turfRepository.save(turf);
        return turfResponse(turf);
    }

    public void deleteById(Long id) throws NotFoundException {
        Turf turf = findByIdTurf(id);
        turfRepository.delete(turf);
    }
    private TurfResponse turfResponse(Turf turf) {
        return TurfResponse.builder().
                id(turf.getUserId()).
                name(turf.getName())
                .image(turf.getImage())
                .clubStatus(turf.getClubStatus())
                .price(turf.getPrice())
                .description(turf.getDescription())
                .userId(turf.getUserId())
                .nightPrice(turf.getNightPrice())
                .userId(turf.getUserId())
                .build();
    }

    public User getAuthentication() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }

    public Turf mapToEntity(TurfRequest request) {
        Turf turf = new Turf();
         turf.setDescription(request.getDescription());
        turf.setUser(request.getUser());
        turf.setImage(request.getImage());
        turf.setName(request.getName());
        turf.setNightPrice(request.getNightPrice());
        turf.setPrice(request.getPrice());
        turf.setSportComplexId(request.getComplexId());
        turf.setClubStatus(request.getClubStatus());
        return turf;
    }

    public TurfResponse mapToResponse(Turf turf) {
        if (turf == null) {
            return null;
        }
        TurfResponse response = new TurfResponse();
        response.setPrice(turf.getPrice());
        response.setId(turf.getId());
        response.setComplexId(turf.getSportComplexId());
        response.setImage(turf.getImage());
        response.setName(turf.getName());
        response.setDescription(turf.getDescription());
        response.setClubStatus(turf.getClubStatus());
        response.setUserId(turf.getUserId());
        response.setNightPrice(turf.getNightPrice());
        return response;
    }

}

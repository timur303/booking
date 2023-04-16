package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.SportComplexRequest;
import kg.kadyrbekov.dto.SportComplexResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.SportComplex;
import kg.kadyrbekov.repository.SportComplexRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportComplexService {

    private final SportComplexRepository complexRepository;


    private final UserRepository userRepository;

    public User getAuthentication() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email not found"));
    }


    public SportComplexResponse create(SportComplexRequest request) throws NotFoundException {
        User user = getAuthentication();
        SportComplex existingComplexWithManagerId = complexRepository.findByComplexManagerId(request.getComplexManagerId()).orElse(null);
        if (existingComplexWithManagerId != null && !existingComplexWithManagerId.getId().equals(user.getId())) {
            throw new RuntimeException("The clubManagerId is already assigned to another club");
        }
        SportComplex complex = mapToEntity(request);
        complex.setUser(user);
        complex.setUserId(user.getId());
        complexRepository.save(complex);
        return mapToResponse(complex);
    }

    public SportComplexResponse update(SportComplexRequest request, Long id) throws NotFoundException {
        SportComplex complex = findByIdComplex(id);
        complex.setComplexName(request.getComplexName());
        complex.setState(request.getState());
        complex.setLogo(request.getLogo());
        complex.setPhotos(request.getPhotos());
        complex.setManagerName(request.getManagerName());
        complex.setDescription(request.getDescription());
        complex.setStreet(request.getStreet());
        complex.setHomeNumber(request.getHomeNumber());
        complex.setCity(request.getCity());
        complex.setPhoneNumber(request.getPhoneNumber());
        complexRepository.save(complex);
        return mapToResponse(complex);
    }

    public void deleteById(Long id) throws NotFoundException {
        SportComplex sportComplex = findByIdComplex(id);
        complexRepository.delete(sportComplex);
    }

    public SportComplex findByIdComplex(Long id) throws NotFoundException {
        return complexRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Sport complex by id not found " + id));
    }

    public SportComplex mapToEntity(SportComplexRequest request) {
        SportComplex complex = new SportComplex();
        complex.setUser(request.getUser());
        complex.setLogo(request.getLogo());
        complex.setCity(request.getCity());
        complex.setDescription(request.getDescription());
        complex.setHomeNumber(request.getHomeNumber());
        complex.setPhoneNumber(request.getPhoneNumber());
        complex.setStreet(request.getStreet());
        complex.setManagerName(request.getManagerName());
        complex.setComplexName(request.getComplexName());
        complex.setReviews(request.getReviews());
        complex.setPhotos(request.getPhotos());
        complex.setComplexManagerId(request.getComplexManagerId());
        return complex;
    }

    public SportComplexResponse mapToResponse(SportComplex complex) {

        if (complex == null) {
            return null;
        }

        SportComplexResponse response = new SportComplexResponse();
        response.setId(complex.getId());
        response.setCity(complex.getCity());
        response.setStreet(complex.getStreet());
        response.setManagerName(complex.getManagerName());
        response.setHomeNumber(complex.getHomeNumber());
        response.setLogo(complex.getLogo());
        response.setDescription(complex.getDescription());
        response.setComplexName(complex.getComplexName());
        response.setReviews(complex.getReviews());
        response.setPhotos(complex.getPhotos());
        response.setUserId(complex.getUserId());
        response.setComplexManagerId(complex.getComplexManagerId());
        return response;
    }

    public SportComplexResponse complexResponse(SportComplex complex) {
        return SportComplexResponse.builder().
                id(complex.getId())
                .complexName(complex.getComplexName())
                .city(complex.getCity())
                .state(complex.getState())
                .description(complex.getDescription())
                .logo(complex.getLogo())
                .street(complex.getStreet())
                .homeNumber(complex.getHomeNumber())
                .reviews(complex.getReviews())
                .managerName(complex.getManagerName())
                .phoneNumber(complex.getPhoneNumber())
                .build();
    }
}

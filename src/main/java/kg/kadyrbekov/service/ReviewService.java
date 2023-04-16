package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ReviewRequest;
import kg.kadyrbekov.dto.ReviewResponse;
import kg.kadyrbekov.exception.NotFoundException;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.model.entity.SportComplex;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.ReviewRepository;
import kg.kadyrbekov.repository.SportComplexRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final SportComplexRepository complexRepository;

    private final ClubRepository clubRepository;

    public User findByIdUser(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found", userId)));
    }

    public ReviewResponse create(ReviewRequest request, Long clubId) throws NotFoundException {
        User user = getAuthentication();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("Club with id not found"));

        Review review = mapToEntity(request);
        review.setClub(club);
        review.setClubId(clubId);
        review.setUser(user);
        reviewRepository.save(review);
        return response(review);

    }

    public ReviewResponse update(ReviewRequest request, Long id) throws NotFoundException {
        Review review = findByIdReview(id);
        review.setReview(request.getReview());
        review.setStarRating(request.getStarRating());
        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public void deleteById(Long id) throws NotFoundException {
        Review review = findByIdReview(id);
        reviewRepository.delete(review);
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public ReviewResponse createComplex(ReviewRequest request, Long complexId) throws NotFoundException {
        User user = getAuthentication();
        SportComplex complex = complexRepository.findById(complexId)
                .orElseThrow(() -> new NotFoundException("Club with id not found"));

        Review review = mapToEntity(request);
        review.setSportComplex(complex);
        review.setComplexId(complexId);
        review.setUser(user);
        reviewRepository.save(review);
        return response(review);

    }

    public ReviewResponse updateComplex(ReviewRequest request, Long id) throws NotFoundException {
        Review review = findByIdReview(id);
        review.setReview(request.getReview());
        review.setStarRating(request.getStarRating());
        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public void deleteByIdComplex(Long id) throws NotFoundException {
        Review review = findByIdReview(id);
        reviewRepository.delete(review);
    }

    public Review findByIdReview(Long id) throws NotFoundException {
        return reviewRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Review with id not found", id)));
    }

    public Review mapToEntity(ReviewRequest request) {
        Review review = new Review();
        BeanUtils.copyProperties(request, review);
        review.setUser(request.getUser());
        review.setClub(request.getClubs());
        review.setSportComplex(request.getComplex());
        return review;
    }


    public ReviewResponse response(Review review) {
        if (review == null) {
            return null;
        }
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setReview(review.getReview());
        response.setStarRating(review.getStarRating());
        response.setClubId(review.getClubId());
        response.setComplexId(review.getComplexId());
        return response;
    }

    public ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .starRating(review.getStarRating())
                .review(review.getReview())
                .complexId(review.getComplexId())
                .clubId(review.getClubId())
                .build();
    }

}

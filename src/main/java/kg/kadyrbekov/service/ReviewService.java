package kg.kadyrbekov.service;

import kg.kadyrbekov.dto.ReviewRequest;
import kg.kadyrbekov.dto.ReviewResponse;
import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Club;
import kg.kadyrbekov.model.entity.Review;
import kg.kadyrbekov.repository.ClubRepository;
import kg.kadyrbekov.repository.ReviewRepository;
import kg.kadyrbekov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final ClubRepository clubRepository;

    public User findByIdUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException(String.format("User with id not found", userId)));
    }

    public ReviewResponse create(ReviewRequest request, Long clubId) {
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

    public ReviewResponse update(ReviewRequest request, Long id) {
        Review review = findByIdReview(id);
        review.setReview(request.getReview());
        review.setStarRating(request.getStarRating());

        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public void deleteById(Long id) {
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

    public Review findByIdReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Review with id not found", id)));
    }

    public Review mapToEntity(ReviewRequest request) {
        Review review = new Review();
        BeanUtils.copyProperties(request, review);
        review.setUser(request.getUser());
        review.setClub(request.getClubs());

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

        return response;
    }

    public ReviewResponse mapToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .starRating(review.getStarRating())
                .review(review.getReview())
                .clubId(review.getClubId())
                .build();
    }

}

package com.project.reviewms.reviews;


import java.util.List;

import com.project.reviewms.reviews.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private ReviewMessageProducer  reviewMessageProducer;

    public ReviewController(ReviewService reviewService,  ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }


    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId),
                                    HttpStatus.OK);
    } 

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review) {
        boolean isCreated = reviewService.addReview(companyId, review);
        if(isCreated){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review added successfully ", HttpStatus.CREATED);}
        else
            return new ResponseEntity<>("Review Not Saved ", HttpStatus.NOT_FOUND);                                    
    }
    
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
    Review review = reviewService.getReviewById(reviewId);
    return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review){
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
        if(isReviewUpdated)
            return new ResponseEntity<>("Review updated Successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Review cannot be updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(isReviewDeleted)
            return new ResponseEntity<>("Review deleted Successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Review cannot be deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }


}

package blps.labs.delegate;

import blps.labs.entity.RejectedUserReview;
import blps.labs.entity.Review;
import blps.labs.service.RejectedUserReviewService;
import blps.labs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class CreateReportDelegate implements JavaDelegate {
    private final ReviewService reviewService;
    private final RejectedUserReviewService rejectedUserReviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Boolean approved = (Boolean) delegateExecution.getVariable("isApproved");
        Long id = Long.valueOf((Integer) delegateExecution.getVariable("reviewId"));
        String message = (String) delegateExecution.getVariable("message");
        Review review = reviewService.findReviewById(id);
        if (!approved && message != null && !message.isEmpty()) {
            RejectedUserReview rejectedReview = rejectedUserReviewService.createRejectedReview(review, message);
            if (rejectedReview != null)
                rejectedUserReviewService.save(rejectedReview);
        }
        log.info("Rejected user review handled");
    }
}

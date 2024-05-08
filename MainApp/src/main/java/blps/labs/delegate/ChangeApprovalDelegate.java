package blps.labs.delegate;

import blps.labs.entity.Review;
import blps.labs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class ChangeApprovalDelegate implements JavaDelegate {
    private final ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Boolean approved = (Boolean) delegateExecution.getVariable("isApproved");
        Long id = Long.valueOf((Integer) delegateExecution.getVariable("reviewId"));
        Review review = reviewService.findReviewById(id);
        review.setApproved(approved);
        log.info("User review №{} change approval to {}", id, approved);
        //todo
//        messageService.sendMessageWhenReviewChecked(id, message, Boolean.valueOf(approved));
    }
}

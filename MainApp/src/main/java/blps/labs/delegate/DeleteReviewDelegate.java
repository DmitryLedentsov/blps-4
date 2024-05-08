package blps.labs.delegate;

import blps.labs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class DeleteReviewDelegate implements JavaDelegate {
    private final ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Long id = Long.valueOf((Integer) delegateExecution.getVariable("reviewId"));
        reviewService.deleteReviewById(id);
        log.info("Review with id {} deleted", id);
    }
}

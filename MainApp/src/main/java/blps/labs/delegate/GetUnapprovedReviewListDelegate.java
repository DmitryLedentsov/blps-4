package blps.labs.delegate;

import blps.labs.entity.Review;
import blps.labs.service.ReviewService;
import camundajar.impl.com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.util.List;

@Slf4j
@Named
@RequiredArgsConstructor
public class GetUnapprovedReviewListDelegate implements JavaDelegate {
    private final ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            List<Review> reviews = reviewService.findAllByApproved(false);
            String json = new Gson().toJson(reviews);
            delegateExecution.setVariable("unapprovedReviews", json);
            log.info("Getting list of reviews with {} approval.", false);
        } catch (Exception e) {
            log.error("get reviews error: {}", e.getMessage());
        }
    }
}

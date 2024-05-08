package blps.labs.delegate;

import blps.labs.entity.Car;
import blps.labs.entity.Review;
import blps.labs.message.service.MessageService;
import blps.labs.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Named
@RequiredArgsConstructor
public class AddReviewDelegate implements JavaDelegate {
    private final ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        String authorName = (String) delegateExecution.getVariable("authorName");
        if (authorName == null)
            authorName = (String) delegateExecution.getVariable("username");
        String carModel = (String) delegateExecution.getVariable("carModel");
        String modification = (String) delegateExecution.getVariable("modification");
        Date manufactureYear = formatter.parse((String) delegateExecution.getVariable("manufactureYear"));
        String reviewText = (String) delegateExecution.getVariable("reviewText");
        String pros = (String) delegateExecution.getVariable("pros");
        String cons = (String) delegateExecution.getVariable("cons");
        String advice = (String) delegateExecution.getVariable("advice");
        Date ownershipDate = formatter.parse((String) delegateExecution.getVariable("ownershipDate"));
        Integer mileage = (Integer) delegateExecution.getVariable("mileage");
        boolean approved = false;
        log.info("Request to add new review");
        Car car = new Car(carModel, modification, manufactureYear, ownershipDate, mileage);
        Review review = new Review(authorName, reviewText, pros, cons, advice, approved, car);
        review.setCar(car);
        reviewService.saveReview(review);
        delegateExecution.setVariable("addedReviewId", review.getId());
        log.info("Review by {} added successfully.", review.getAuthorName());

    }
}

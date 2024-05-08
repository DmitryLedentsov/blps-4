package blps.labs.delegate;

import blps.labs.entity.Review;
import blps.labs.entity.User;
import blps.labs.message.model.AddReviewMessage;
import blps.labs.message.rabbitmq.RabbitMQSender;
import blps.labs.message.service.MessageService;
import blps.labs.service.ReviewService;
import blps.labs.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class AddReviewMessageSendDelegate implements JavaDelegate {
    private final ReviewService reviewService;
    private final MessageService messageService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Long id = (Long) delegateExecution.getVariable("addedReviewId");
        Review review = reviewService.findReviewById(id);
        messageService.sendMessageWhenReviewAdd(review);
    }
}

package blps.labs.delegate;

import blps.labs.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Slf4j
@Named
@RequiredArgsConstructor
public class SendSpamDelegate implements JavaDelegate {
    private final MessageService messageService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("It's time to send spam!");
        try {
            messageService.sendMessageWithTheNewestReviews();
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}

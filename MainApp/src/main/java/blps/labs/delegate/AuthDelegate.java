package blps.labs.delegate;

import blps.labs.entity.Role;
import blps.labs.entity.User;
import blps.labs.service.RoleService;
import blps.labs.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Named
@RequiredArgsConstructor
public class AuthDelegate implements JavaDelegate {
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        delegateExecution.setVariable("signedIn", "false");
        delegateExecution.setVariable("signedUp", "false");
        String username = (String) delegateExecution.getVariable("username");
        String password = (String) delegateExecution.getVariable("password");
        Boolean isSignUp = (Boolean) delegateExecution.getVariable("isSignUp");
        try {
            if (isSignUp != null && isSignUp) {
                User user = new User(username, password);
                Role role = roleService.findByName("ROLE_USER");
                user.setRoles(Collections.singleton(role));
                log.info("Successful {} sign up.", user.getUsername());
                boolean isSaved = userService.saveUser(user);
                if (!isSaved) {
                    log.info("SignUp for user {} failed. User with same username exists", username);
                    throw new BpmnError("signUpFailed", "Пользователь с таким именем уже существует");
                }
                delegateExecution.setVariable("signedUp", "true");
            } else {
                boolean isModerator = Boolean.parseBoolean((String) delegateExecution.getVariable("isModerator"));
                if (isModerator) {
                    List<String> rolesNames = userService.findUserByUsername(username).getRoles().stream().map(Role::getName).collect(Collectors.toList());
                    if (!rolesNames.contains("ROLE_MODERATOR")) {
                        log.info("SignIn for user {} failed. User with same username is not moderator", username);
                        throw new BpmnError("signInFailed", "Пользователь не модератор");
                    }
                }
                final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Successful {} sign in.", username);
                delegateExecution.setVariable("signedIn", "true");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

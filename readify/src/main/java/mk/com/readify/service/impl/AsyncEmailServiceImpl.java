package mk.com.readify.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mk.com.readify.model.enums.EmailTemplateName;
import mk.com.readify.service.AsyncEmailService;
import mk.com.readify.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AsyncEmailServiceImpl implements AsyncEmailService {

    private final EmailService emailService;

    @Async
    @Override
    public void sendAsyncEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl, String activationCode, String subject) throws MessagingException {
        emailService.sendEmail(to, username, emailTemplate, confirmationUrl, activationCode, subject);
    }
}

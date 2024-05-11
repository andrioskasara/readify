package mk.com.readify.service;

import jakarta.mail.MessagingException;
import mk.com.readify.model.enums.EmailTemplateName;

public interface EmailService {
    void sendEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl, String activationCode, String subject) throws MessagingException;
}

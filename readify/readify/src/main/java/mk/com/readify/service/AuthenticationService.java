package mk.com.readify.service;

import jakarta.mail.MessagingException;
import mk.com.readify.model.request.RegistrationRequest;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;
}

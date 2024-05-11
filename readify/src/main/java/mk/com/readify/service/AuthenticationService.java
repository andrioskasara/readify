package mk.com.readify.service;

import jakarta.mail.MessagingException;
import mk.com.readify.model.request.AuthenticationRequest;
import mk.com.readify.model.request.RegistrationRequest;
import mk.com.readify.model.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void activateAccount(String token) throws MessagingException;
}

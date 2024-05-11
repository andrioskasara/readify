package mk.com.readify.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class RegistrationRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Ensure email is in the correct format (e.g., example@example.com).")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 8, message = "Ensure password is at least 8 characters long.")
    @NotBlank(message = "Password is required")
    private String password;
}

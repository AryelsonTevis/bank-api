package api.bank.bankapi.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatePost {
    @NotEmpty(message = "Cpf cannot be empty")
    @Schema(description = "This is the user cpf", example = "01234567891")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Cpf must have 11 digits")
    @CPF(message = "Invalid Cpf")
    private String cpf;

    @NotEmpty(message = "Birth date cannot be empty")
    @Schema(description = "This is the user birthday",example = "dd/mm/YYYY")
    @Pattern(regexp = "\\d[01-31]{2}/\\d[01-12]{2}/\\d{4}", message = "Date format invalid ,format dd/mm/yyyy")
    private String birthDate;

    @NotEmpty(message = "Email cannot be empty")
    @Schema(description = "This is the user email", example = "exemple@test.com")
    @Email(regexp = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$",message = "Invalid E-mail")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "This is the user name ")
    private String name;

    @NotEmpty(message = "Password cannot be empty")
    @Schema(description = "This is the user password ")
    private String password;

    @NotEmpty(message = "Phone cannot be empty")
    @Schema(description = "This is the user phone number",examples = {"(XX)XXXXX-XXXX","XXXXXXXXXXX"} )
    @Pattern(regexp = "^\\(?[1-9]{2}\\)?\\s?(9\\d{4}|[2-5]\\d{3})-?\\d{4}$",
            message = "invalid phone number")
    private String phone;
}

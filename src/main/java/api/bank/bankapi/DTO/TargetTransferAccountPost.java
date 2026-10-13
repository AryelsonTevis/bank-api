package api.bank.bankapi.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetTransferAccountPost {
    @NotEmpty(message = "Cpf cannot be empty")
    @Schema(description = "This is the cpf target account", example = "01234567891 or 012.345.678-91")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Cpf must have 11 digits")
    @CPF(message = "Invalid Cpf")
    private String cpf;

}

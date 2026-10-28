package api.bank.bankapi.DTO;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private String name;

    private String cpf;

    @Digits(integer = 12, fraction = 2)
    private BigDecimal balance;

    @Digits(integer = 12, fraction = 2)
    private BigDecimal credit;

    private String token;
}

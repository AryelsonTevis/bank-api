package api.bank.bankapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private String name;

    private String cpf;

    private String balance;

    private String credit;

    private String token;
}

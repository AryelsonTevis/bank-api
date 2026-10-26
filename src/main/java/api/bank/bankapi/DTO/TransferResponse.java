package api.bank.bankapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private BigDecimal amount;

    private String date;

    private String targetAccountCpf;

    private String originAccountCpf;
}

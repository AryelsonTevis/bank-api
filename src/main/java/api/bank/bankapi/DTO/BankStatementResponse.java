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
public class BankStatementResponse {
    private String targetCpfFromAccount;
    private String targetNameFromAccount;
    private String date;
    private String amount;
}

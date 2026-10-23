package api.bank.bankapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementResponse {

    private String title;

    private String CpfFromAccount;

    private String NameFromAccount;

    private String date;

    private String amount;
}

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
public class TransferResponse {

    @Digits(integer = 12, fraction = 2)
    private BigDecimal amount;

    private String date;

    private Long targetUserId;

    private Long originUserId;
}

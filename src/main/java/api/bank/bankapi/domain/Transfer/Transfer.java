package api.bank.bankapi.domain.Transfer;

import api.bank.bankapi.domain.account.Account;
import api.bank.bankapi.domain.user.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_account", referencedColumnName = "id")
    @NotNull(message = "Account user origin not referred")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "target_account", referencedColumnName = "id")
    @NotNull(message = "Account user target not referred")
    private Account targetAccount;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String date;

    private BigDecimal accountOriginBalanceBefore;

    private BigDecimal accountOriginBalanceAfter;

    private BigDecimal accountTargetBalanceBefore;

    private BigDecimal accountTargetBalanceAfter;
}

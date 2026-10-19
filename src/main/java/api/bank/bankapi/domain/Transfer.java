package api.bank.bankapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Users originUsers;

    @ManyToOne
    @JoinColumn(name = "target_account", referencedColumnName = "id")
    @NotNull(message = "Account user target not referred")
    private Users targetUsers;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String date;

    private BigDecimal accountOriginBalanceBefore;

    private BigDecimal accountOriginBalanceAfter;

    private BigDecimal accountTargetBalanceBefore;

    private BigDecimal accountTargetBalanceAfter;
}

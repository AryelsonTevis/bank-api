package api.bank.bankapi.domain;

import api.bank.bankapi.DTO.AccountUserResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "credit_limit")
    private double creditLimit;

    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    @NotNull(message = "Account user not referred")
    private Users users;
}

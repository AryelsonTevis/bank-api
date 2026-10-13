package api.bank.bankapi.repository;

import api.bank.bankapi.domain.Account;
import api.bank.bankapi.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * FROM account WHERE cpf = :cpf", nativeQuery = true)
    Account findByCpf(@Param("cpf") String cpf);
}

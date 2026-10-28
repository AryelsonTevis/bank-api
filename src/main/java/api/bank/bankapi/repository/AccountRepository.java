package api.bank.bankapi.repository;

import api.bank.bankapi.domain.account.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT *FROM account WHERE cpf = :cpf", nativeQuery = true)
    Account findByCpf(@Param("cpf") String cpf);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT a FROM Account a WHERE a.users.cpf = :cpf")
    Account findByCpfForUpdate(@Param("cpf") String cpf);

}

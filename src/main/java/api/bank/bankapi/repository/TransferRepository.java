package api.bank.bankapi.repository;

import api.bank.bankapi.domain.Transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}

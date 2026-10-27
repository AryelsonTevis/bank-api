package api.bank.bankapi.repository;

import api.bank.bankapi.DTO.TransferResponse;
import api.bank.bankapi.domain.Transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "SELECT amount, date, target_account,  origin_account FROM transfer WHERE origin_account = :originId OR target_account = :originId ORDER BY date DESC;", nativeQuery = true)
    List<TransferResponse> findTransferByUserId(@Param("originId") Long originId);


}

package api.bank.bankapi.repository;

import api.bank.bankapi.DTO.TransferResponse;
import api.bank.bankapi.domain.Transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "SELECT amount, date, target_account, origin_account FROM transfer WHERE origin_account = :originCpf OR target_account =:originCpf ORDER BY date DESC", nativeQuery = true)
    List<TransferResponse> findByTransferAccount(@Param("originCpf") String originCpf);

}

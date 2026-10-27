package api.bank.bankapi.repository;

import api.bank.bankapi.domain.user.UserStatus;
import api.bank.bankapi.domain.user.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByCpfAndActive(String cpf, UserStatus status);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users set active = 'DISABLED' WHERE cpf =:cpf and active = 'ENABLED'", nativeQuery = true)
    void disableUser(@Param("cpf") String cpf);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users set active = 'ENABLED' WHERE cpf =:cpf and active = 'DISABLED'", nativeQuery = true)
    void enableUser(@Param("cpf") String cpf);

    Users findByCpf(String cpf);
}

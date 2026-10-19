package api.bank.bankapi.repository;

import api.bank.bankapi.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT * FROM users WHERE cpf = :cpf and active = 1", nativeQuery = true)
    Users findByCpfAndActiveTrue(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM users WHERE id = :id and active = 1", nativeQuery = true)
    Users findByIdAndActiveTrue(@Param("id") Long id);
}

package api.bank.bankapi.repository;

import api.bank.bankapi.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT * FROM users WHERE cpf = :cpf and active = 1", nativeQuery = true)
    Users findByCpfAndActiveTrue(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM users u WHERE cpf = :cpf and active = 1", nativeQuery = true)
    Users findByLogin(@Param("cpf") String cpf);
}

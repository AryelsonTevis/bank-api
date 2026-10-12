package api.bank.bankapi.repository;

import api.bank.bankapi.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<Users, Long> {
}

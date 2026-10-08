package api.bank.bankapi.repository;

import api.bank.bankapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}

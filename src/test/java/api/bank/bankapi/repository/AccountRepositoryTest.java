package api.bank.bankapi.repository;

import api.bank.bankapi.domain.Account;
import api.bank.bankapi.domain.Users;
import api.bank.bankapi.util.AccountCreator;
import api.bank.bankapi.util.UserCreator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@Log4j2
@DataJpaTest
@DisplayName("Tests for Account repository")
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save persist Account when Successful")
    void save_PersistAccount_WhenSuccessful() {
        Users userToBeSaved = UserCreator.createUserToBeSaved();

        Users userSaved = this.userRepository.save(userToBeSaved);
        Account accountToBeSaved = AccountCreator.createAccount();
        accountToBeSaved.setUsers(userSaved);
        Account accountSaved = this.accountRepository.save(accountToBeSaved);

        Assertions.assertThat(accountSaved).isNotNull();

        Assertions.assertThat(accountSaved.getId()).isNotNull();

        Assertions.assertThat(accountSaved.getUsers().getName()).isEqualTo(userToBeSaved.getName());

        Assertions.assertThat(accountSaved).isEqualTo(accountToBeSaved);
    }

    @Test
    @DisplayName("Error saving Account when user is not referenced user")
    void error_SavingAccount_WhenUserIsNotReferencedUser() {
        Account accountToBeSaved = AccountCreator.createAccount();

        Assertions.assertThatThrownBy(() -> this.accountRepository.save(accountToBeSaved)).isInstanceOf(ConstraintViolationException.class);
    }
}

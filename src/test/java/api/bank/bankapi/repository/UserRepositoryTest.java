package api.bank.bankapi.repository;

import api.bank.bankapi.domain.user.Users;
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
@DisplayName("Tests for User Repository")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Save persist User when Successful")
    void save_PersistUser_WhenSuccessful(){
        Users userToBeSaved = UserCreator.createUserToBeSaved();

        Users userSaved = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();

        Assertions.assertThat(userSaved.getId()).isNotNull();

        Assertions.assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());

        Assertions.assertThat(userSaved).isEqualTo(userToBeSaved);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){
        Users userWithNameEmpty = UserCreator.createUserWithNameEmpty();

        Assertions.assertThatThrownBy(()-> this.userRepository.save(userWithNameEmpty)).isInstanceOf(ConstraintViolationException.class);
    }
    @Test
    @DisplayName("Save throw ConstraintViolationException when cpf is empty")
    void save_ThrowConstraintViolationException_WhenCpfIsEmpty(){
        Users userWithEmptyCpf = UserCreator.createUserWithEmptyCpf();

        Assertions.assertThatThrownBy(()-> this.userRepository.save(userWithEmptyCpf)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when email is empty")
    void save_ThrowEConstraintViolationException_WhenEmailIsEmpty(){
        Users userWithEmptyEmail = UserCreator.createUserWithEmptyEmail();

        Assertions.assertThatThrownBy(()-> this.userRepository.save(userWithEmptyEmail)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when phone number is empty")
    void save_ThrowConstraintViolationException_WhenPhoneNumberIsEmpty(){
        Users userWithEmptyPhoneNumber = UserCreator.createUserWithEmptyPhoneNumber();

        Assertions.assertThatThrownBy(()-> this.userRepository.save(userWithEmptyPhoneNumber)).isInstanceOf(ConstraintViolationException.class);
    }


}

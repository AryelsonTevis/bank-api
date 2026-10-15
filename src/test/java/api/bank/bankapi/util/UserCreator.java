package api.bank.bankapi.util;

import api.bank.bankapi.domain.Users;
import api.bank.bankapi.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;

public class UserCreator {

    public static Users createUserToBeSaved() {
        return Users.builder()
                .active(true)
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserToBeSavedForIntegration() {
        return Users.builder()
                .active(true)
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }

    public static Users createValidUser() {
        return Users.builder()
                .id(1L)
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }

    public static Users createValidUserUpdated() {
        return Users.builder()
                .id(1L)
                .cpf("12345678909")
                .name("Teste 2")
                .password("Teste321!")
                .email("Astaroth@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)888888888")
                .build();
    }
    public static Users createUserWithNameEmpty() {
        return Users.builder()
                .cpf("1234567890")
                .name("")
                .password("Teste123!")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserWithEmptyCpf() {
        return Users.builder()
                .cpf("")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserWithEmptyEmail() {
        return Users.builder()
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserWithEmptyPhoneNumber() {
        return Users.builder()
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("")
                .build();
    }

    public static Users createUserWithInvalidCpf() {
        return Users.builder()
                .cpf("1234567890")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserWithInvalidEmail() {
        return Users.builder()
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("(34)999999999")
                .build();
    }
    public static Users createUserWithInvalidPhoneNumber() {
        return Users.builder()
                .cpf("12345678909")
                .name("Teste")
                .password("Teste123!")
                .email("teste@gmail.com")
                .date_birth(Date.valueOf("2000-06-01"))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone("999999999")
                .build();
    }
}

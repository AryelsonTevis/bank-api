package api.bank.bankapi.service;

import api.bank.bankapi.DTO.UserLoginPostRequest;
import api.bank.bankapi.DTO.UserPostRequest;
import api.bank.bankapi.DTO.UserResponse;
import api.bank.bankapi.domain.Account;
import api.bank.bankapi.domain.Users;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static api.bank.bankapi.service.CpfFormat.formatCpf;
import static api.bank.bankapi.service.PhoneFormat.formatPhone;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public UserResponse saveUser(UserPostRequest userCreatePost) {
        String CpfToBeSaved = formatCpf(userCreatePost.getCpf());
        String phoneToBeSaved = formatPhone(userCreatePost.getPhone());

        LocalDate dateBirth = verifyAgeIsValidOrThrowIllegalArgumentException(userCreatePost.getBirthDate());

        String passwordEntered = userCreatePost.getPassword();

        Users userToBeSaved = Users.builder()
                .cpf(CpfToBeSaved)
                .name(userCreatePost.getName())
                .password(passwordEncoder.encode(passwordEntered))
                .email(userCreatePost.getEmail())
                .dateBirth(Date.valueOf(dateBirth))
                .dateJoined(Date.valueOf(LocalDate.now()))
                .phone(phoneToBeSaved)
                .active(true)
                .build();

        Users savedUser = userRepository.save(userToBeSaved);
        Account createAccount = Account.builder().balance(BigDecimal.ZERO).creditLimit(new BigDecimal("50.00")).users(savedUser).build();

        Account savedAccount = accountRepository.save(createAccount);
        return UserResponse.builder().name(savedUser.getName()).cpf(savedUser.getCpf()).balance(String.format("R$ %.2f", savedAccount.getBalance())).credit(String.format("R $%.2f",savedAccount.getCreditLimit())).build();
    }

    public UserResponse login(UserLoginPostRequest userLogin) {

        Users userResponse = userRepository.findByCpfAndActiveTrue(formatCpf(userLogin.getCpf()));

        if ( userResponse != null && passwordMatch(userLogin.getPassword(), userResponse.getPassword())) {
            Account account = accountRepository.findByCpf(userResponse.getCpf());
            return UserResponse.builder()
                    .name(userResponse.getName())
                    .cpf(formatCpf(userResponse.getCpf()))
                    .balance("R$ "+df.format(account.getBalance()))
                    .credit("R$ "+df.format(account.getCreditLimit()))
                    .build();
        }
        throw new IllegalArgumentException("Cpf/Password dont matches");
    }

    public boolean passwordMatch(String passwordEntered, String passwordSaved) {
        return passwordEncoder.matches(passwordEntered, passwordSaved);
    }

    private LocalDate verifyAgeIsValidOrThrowIllegalArgumentException(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (Period.between(date, LocalDate.now()).getYears() >= 16) return date;
        throw new IllegalArgumentException("Age less than 16 years old");
    }

}

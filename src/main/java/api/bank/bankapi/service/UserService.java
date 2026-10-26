package api.bank.bankapi.service;

import api.bank.bankapi.DTO.*;
import api.bank.bankapi.domain.account.Account;
import api.bank.bankapi.domain.user.UserRole;
import api.bank.bankapi.domain.user.UserStatus;
import api.bank.bankapi.domain.user.Users;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.UserRepository;
import api.bank.bankapi.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static api.bank.bankapi.service.CpfFormat.formatCpf;
import static api.bank.bankapi.service.PhoneFormat.formatPhone;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public UserResponse saveUser(UserRegisterRequest userCreatePost) {

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
                .active(UserStatus.ENABLED)
                .role(UserRole.USER)
                .build();

        Users savedUser = userRepository.save(userToBeSaved);

        Account createAccount = Account.builder().balance(BigDecimal.ZERO).creditLimit(new BigDecimal("50.00")).users(savedUser).build();

        Account savedAccount = accountRepository.save(createAccount);

        return UserResponse.builder().name(savedUser.getName()).cpf(savedUser.getCpf()).balance(String.format("R$ %.2f", savedAccount.getBalance())).credit(String.format("R $%.2f", savedAccount.getCreditLimit())).build();
    }

    public UserResponse saveUserAdmin(UserRegisterRequest userCreatePost, String password) {

        if (!tokenService.chekPermissionToCreateUserAdmin(password)) throw new IllegalArgumentException("You cant create user admin");

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
                .active(UserStatus.ENABLED)
                .role(UserRole.ADMIN)
                .build();

        Users savedUser = userRepository.save(userToBeSaved);

        Account createAccount = Account.builder().balance(BigDecimal.valueOf(100)).creditLimit(new BigDecimal("100.00")).users(savedUser).build();

        Account savedAccount = accountRepository.save(createAccount);

        return UserResponse.builder().name(savedUser.getName()).cpf(savedUser.getCpf()).balance(String.format("R$ %.2f", savedAccount.getBalance())).credit(String.format("R $%.2f", savedAccount.getCreditLimit())).build();
    }

    public UserLoginResponse login(UserLoginRequest userLogin) {

        var login = new UsernamePasswordAuthenticationToken(formatCpf(userLogin.getCpf()), userLogin.getPassword());

        var auth = this.authenticationManager.authenticate(login);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        Users userResponse = userRepository.findByLogin(formatCpf(userLogin.getCpf()));

        Account account = accountRepository.findByCpf(formatCpf(userLogin.getCpf()));

        return UserLoginResponse.builder()
                .name(userResponse.getName())
                .cpf(formatCpf(userResponse.getCpf()))
                .balance("R$ " + df.format(account.getBalance()))
                .credit("R$ " + df.format(account.getCreditLimit()))
                .token(token)
                .build();

    }

    public void disableUser(String token){

        String cpf = extractCpfFromToken(token);

        Account account = accountRepository.findByCpf(formatCpf(cpf));

        if (account.getBalance().compareTo(BigDecimal.ZERO)!=0) throw new RuntimeException("You cannot disable this user because the balance not is not zero");

        userRepository.disableUser(cpf);
    }

    public void enableUser(String token){

        String cpf = extractCpfFromToken(token);

        userRepository.enableUser(cpf);
    }

    private LocalDate verifyAgeIsValidOrThrowIllegalArgumentException(String birthDate) {

        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if (Period.between(date, LocalDate.now()).getYears() >= 16) return date;

        throw new IllegalArgumentException("Age less than 16 years old");
    }

    public List<AccountUserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(user -> {
            AccountUserResponse response = new AccountUserResponse();
            response.setName(user.getName());
            response.setCpf(user.getCpf());
            return response;
        }).toList();
    }


    private String extractCpfFromToken(String token) {

        return tokenService.validateToken(token.replace("Bearer ", ""));
    }

}

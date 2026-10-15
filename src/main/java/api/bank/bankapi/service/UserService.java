package api.bank.bankapi.service;

import api.bank.bankapi.DTO.*;
import api.bank.bankapi.domain.Account;
import api.bank.bankapi.domain.Transfer;
import api.bank.bankapi.domain.Users;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.TransferRepository;
import api.bank.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public List<Users> listAllUsers() {
        return userRepository.findAll();
    }

    public Users saveUser(UserPostRequest userCreatePost) {
        String CpfToBeSaved = formatCpf(userCreatePost.getCpf());
        String phoneToBeSaved = formatPhone(userCreatePost.getPhone());

        LocalDate dateBirth = verifyAgeIsValidOrThrowIllegalArgumentException(userCreatePost.getBirthDate());

        String passwordEntered = userCreatePost.getPassword();

        Users userToBeSaved = Users.builder()
                .cpf(CpfToBeSaved)
                .name(userCreatePost.getName())
                .password(passwordEncoder.encode(passwordEntered))
                .email(userCreatePost.getEmail())
                .date_birth(Date.valueOf(dateBirth))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone(phoneToBeSaved)
                .active(true)
                .build();

        Users savedUser = userRepository.save(userToBeSaved);
        Account createAccount = Account.builder().balance(0).creditLimit(50.00).users(savedUser).build();

        accountRepository.save(createAccount);
        return savedUser;
    }

    public LoginUserResponse login(UserLoginPostRequest userLogin) {

        Users userResponse = userRepository.findByCpf(formatCpf(userLogin.getCpf()));

        if ( userResponse != null && passwordMatch(userLogin.getPassword(), userResponse.getPassword())) {
            Account account = accountRepository.findByCpf(userResponse.getCpf());
            return LoginUserResponse.builder()
                    .name(userResponse.getName())
                    .cpf(formatCpf(userResponse.getCpf()))
                    .balance("R$ "+df.format(account.getBalance()))
                    .credit("R$ "+df.format(account.getCreditLimit()))
                    .build();
        }
        throw new IllegalArgumentException("Cpf/Password dont matches");
    }

    public AccountUserResponse transfer(TargetTransferAccountPost transferAccountPost){

        Users accountTarget = userRepository.findByCpf(formatCpf(transferAccountPost.getCpf()));
        if(accountTarget != null) return AccountUserResponse.builder()
                .name(accountTarget.getName())
                .cpf(formatCpfResponse(accountTarget.getCpf())).build();
        throw new IllegalArgumentException("User not found");
    }

    public LoginUserResponse responseToTransferAttempt(Long originId,TargetTransferAmountAndTargetAccountPost targetTransferAmountPost) {
        Users userOrigin = userRepository.findByIdActive(originId);

        Users userTarget = userRepository.findByCpf(targetTransferAmountPost.getCpf());

        Account accountOrigin = accountRepository.findByCpf(userOrigin.getCpf());

        Account accountTarget = accountRepository.findByCpf(userTarget.getCpf());

        if (userOrigin.equals(userTarget)) throw new IllegalArgumentException("Users can't be the same");

        if (verifyAmount(accountOrigin, targetTransferAmountPost.getAmount())) {
            Transfer transfer = Transfer.builder()
                    .originUsers(userOrigin)
                    .targetUsers(userTarget)
                    .amount(targetTransferAmountPost.getAmount())
                    .date( dtf.format(LocalDateTime.now(ZoneId.of("UTC"))))
                    .accountOriginBalanceBefore(accountOrigin.getBalance())
                    .accountOriginBalanceAfter(accountOrigin.getBalance() - targetTransferAmountPost.getAmount())
                    .accountTargetBalanceBefore(accountTarget.getBalance())
                    .accountTargetBalanceAfter(accountTarget.getBalance() + targetTransferAmountPost.getAmount())
                    .build();

            accountOrigin.setBalance(accountOrigin.getBalance() - targetTransferAmountPost.getAmount());

            accountTarget.setBalance(accountTarget.getBalance() + targetTransferAmountPost.getAmount());

            accountOrigin = accountRepository.save(accountOrigin);
            accountTarget = accountRepository.save(accountTarget);
            transferRepository.save(transfer);

        }

        return LoginUserResponse.builder()
                .name(userOrigin.getName())
                .cpf(userOrigin.getCpf())
                .balance("R$ "+df.format(accountOrigin.getBalance()))
                .credit("R$ "+df.format(accountOrigin.getCreditLimit())).build();
    }

    public boolean verifyAmount(Account originAccount,double amount) {
        boolean result = false;
        if (amount<=0) throw new  IllegalArgumentException("Amount must be greater than zero");

        if (amount>originAccount.getBalance()) throw new  IllegalArgumentException("Amount exceeds account balance limit");
        result = true;
        return  result;

    }

    public boolean passwordMatch(String passwordEntered, String passwordSaved) {
        return passwordEncoder.matches(passwordEntered, passwordSaved);
    }

    private LocalDate verifyAgeIsValidOrThrowIllegalArgumentException(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        if ((LocalDate.now().getYear() - date.getYear()) >= 16) return date;
        throw new IllegalArgumentException("Age less than 16 years old");
    }

    public static String formatCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatCpfResponse(String cpf) {

        return formatCpf(cpf).replaceFirst("\\d{3}", "***")
                .replaceAll("-\\d{2}", "-**");
    }

    public static String formatPhone(String tel) {
        if (tel.length() == 11) {
            return tel.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        return tel.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }

}

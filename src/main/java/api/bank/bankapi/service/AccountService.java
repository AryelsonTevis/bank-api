package api.bank.bankapi.service;

import api.bank.bankapi.DTO.AccountUserResponse;
import api.bank.bankapi.DTO.TargetTransferAccountPost;
import api.bank.bankapi.DTO.TargetTransferAmountAndTargetAccountPost;
import api.bank.bankapi.DTO.UserResponse;
import api.bank.bankapi.domain.account.Account;
import api.bank.bankapi.domain.Transfer.Transfer;
import api.bank.bankapi.domain.user.Users;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.TransferRepository;
import api.bank.bankapi.repository.UserRepository;
import api.bank.bankapi.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static api.bank.bankapi.service.CpfFormat.formatCpf;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private TokenService tokenService;

    private DecimalMoneyResponse decimalMoneyResponse;

    private FormatCpfResponse formatCpfResponse;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final TransferRepository transferRepository;

    public AccountUserResponse transfer(String token, TargetTransferAccountPost transferAccountPost) {

        Users accountTarget = userRepository.findByCpfAndActiveTrue(formatCpf(transferAccountPost.getCpf()));
        String cpfOrigin = extractOriginCpfFromToken(token);

        if (accountTarget == null) throw new IllegalArgumentException("The target account does not exist or is disabled");
        if(accountTarget.getCpf().equals(cpfOrigin)) throw new IllegalArgumentException("Users can't be the same");
        if (accountTarget != null) return AccountUserResponse.builder()
                .name(accountTarget.getName())
                .cpf(formatCpfResponse.formatCpfResponse(accountTarget.getCpf())).build();
        throw new IllegalArgumentException("User not found");
    }

    @Transactional
    public UserResponse responseToTransferAttempt(String token, TargetTransferAmountAndTargetAccountPost targetTransferAmountPost) {
        String cpfOrigin = extractOriginCpfFromToken(token);


        Users userOrigin = userRepository.findByCpfAndActiveTrue(cpfOrigin);

        if (userOrigin == null) throw new IllegalArgumentException("Transfer is not possible, this user is disabled");

        Users userTarget = userRepository.findByCpfAndActiveTrue(formatCpf(targetTransferAmountPost.getCpf()));

        if (userTarget == null) throw new IllegalArgumentException("User Target not found or is disabled");

        Account accountOrigin = accountRepository.findByCpf(userOrigin.getCpf());

        Account accountTarget = accountRepository.findByCpf(userTarget.getCpf());

        if (userOrigin.equals(userTarget)) throw new IllegalArgumentException("Users can't be the same");

        if (verifyAmount(accountOrigin, targetTransferAmountPost.getAmount())) {
            Transfer transfer = Transfer.builder()
                    .originAccount(accountOrigin)
                    .targetAccount(accountTarget)
                    .amount(targetTransferAmountPost.getAmount())
                    .date(dtf.format(LocalDateTime.now(ZoneId.of("UTC"))))
                    .accountOriginBalanceBefore(accountOrigin.getBalance())
                    .accountOriginBalanceAfter(accountOrigin.getBalance().subtract(targetTransferAmountPost.getAmount()))
                    .accountTargetBalanceBefore(accountTarget.getBalance())
                    .accountTargetBalanceAfter(accountTarget.getBalance().add(targetTransferAmountPost.getAmount()))
                    .build();

            accountOrigin.setBalance(accountOrigin.getBalance().subtract(targetTransferAmountPost.getAmount()));

            accountTarget.setBalance(accountTarget.getBalance().add(targetTransferAmountPost.getAmount()));

            accountOrigin = accountRepository.save(accountOrigin);
            accountRepository.save(accountTarget);
            transferRepository.save(transfer);

        }

        return UserResponse.builder()
                .name(userOrigin.getName())
                .cpf(userOrigin.getCpf())
                .balance(decimalMoneyResponse.formatDecimal(accountOrigin.getBalance()))
                .credit(decimalMoneyResponse.formatDecimal(accountOrigin.getCreditLimit())).build();
    }

    private boolean verifyAmount(Account originAccount, BigDecimal amount) {
        boolean result = false;
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Amount must be greater than zero");

        if (amount.compareTo(originAccount.getBalance()) > 0)
            throw new IllegalArgumentException("Amount exceeds account balance limit");
        result = true;
        return result;

    }



    private String extractOriginCpfFromToken(String token) {
        return tokenService.validateToken(token.replace("Bearer ", ""));
    }
}

package api.bank.bankapi.service;

import api.bank.bankapi.DTO.BankStatementResponse;
import api.bank.bankapi.domain.Transfer.Transfer;
import api.bank.bankapi.domain.account.Account;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.TransferRepository;
import api.bank.bankapi.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferService {
    @Autowired
    private TokenService tokenService;

    private DecimalMoneyResponse decimalMoneyResponse;

    private final TransferRepository transferRepository;

    private final AccountRepository accountRepository;

    public List<BankStatementResponse> getBankStatement(String token) {
        Long idAccountOrigin = accountRepository.findByCpf(extractOriginCpfFromToken(token)).getId();

        return transferRepository.findByOriginAccount(idAccountOrigin).stream().map(tr -> {
            BankStatementResponse response = new BankStatementResponse();
            response.setAmount(decimalMoneyResponse.formatDecimal(tr.getAmount()));
            response.setTargetCpfFromAccount(accountRepository.findById(tr.getTargetAccount()).map(at->at.getUsers().getCpf()).toString().replace("Optional[","").replace("]",""));
            response.setTargetNameFromAccount(accountRepository.findById(tr.getTargetAccount()).map(at-> at.getUsers().getName()).toString().replace("Optional[","").replace("]",""));
            response.setDate(tr.getDate());

            return response;

        }).toList();
    }

    private String extractOriginCpfFromToken(String token) {
        return tokenService.validateToken(token.replace("Bearer ", ""));
    }
}

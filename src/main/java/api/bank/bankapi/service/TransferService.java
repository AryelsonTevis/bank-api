package api.bank.bankapi.service;

import api.bank.bankapi.DTO.BankStatementResponse;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.TransferRepository;
import api.bank.bankapi.repository.UserRepository;
import api.bank.bankapi.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferService {

    @Autowired
    private TokenService tokenService;

    private DecimalMoneyResponse decimalMoneyResponse;

    private FormatCpfResponse formatCpfResponse;

    private final TransferRepository transferRepository;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    public List<BankStatementResponse> getBankStatement(String token) {
        String cpf = extractCpfFromToken(token);
        log.info(cpf);

        String cpfOrigin = accountRepository.findByCpf(cpf).getUsers().getCpf();

        return transferRepository.findByTransferAccount(cpfOrigin).stream().map(tr -> {
            BankStatementResponse response = new BankStatementResponse();

            if (cpfOrigin.equals(tr.getOriginAccountCpf())) {

                response.setTitle("Transferido");

                response.setAmount("-" + decimalMoneyResponse.formatDecimal(tr.getAmount()));

                response.setCpfFromAccount(formatCpfResponse.formatCpfResponse(userRepository.findByCpf(tr.getTargetAccountCpf()).getCpf()));

                response.setNameFromAccount(userRepository.findByCpf(tr.getTargetAccountCpf()).getName());

                response.setDate(tr.getDate());
            } else {

                response.setTitle("Recebido");

                response.setAmount("+" + decimalMoneyResponse.formatDecimal(tr.getAmount()));

                response.setCpfFromAccount(formatCpfResponse.formatCpfResponse(userRepository.findByCpf(tr.getOriginAccountCpf()).getCpf()));

                response.setNameFromAccount(userRepository.findByCpf(tr.getOriginAccountCpf()).getName());

                response.setDate(tr.getDate());
            }
            return response;

        }).toList();
    }



    private String extractCpfFromToken(String token) {

        return tokenService.validateToken(token.replace("Bearer ", ""));
    }
}

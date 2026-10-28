package api.bank.bankapi.service;

import api.bank.bankapi.DTO.BankStatementResponse;
import api.bank.bankapi.DTO.TransferResponse;
import api.bank.bankapi.domain.account.Account;
import api.bank.bankapi.domain.user.Users;
import api.bank.bankapi.repository.AccountRepository;
import api.bank.bankapi.repository.TransferRepository;
import api.bank.bankapi.repository.UserRepository;
import api.bank.bankapi.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    private final AccountRepository accountRepository;

    public List<BankStatementResponse> getBankStatement(Users user) {
        String cpf = user.getCpf();

        Long userAccountId = accountRepository.findByCpf(cpf).getId();


        return transferRepository.findTransferByUserId(userAccountId).stream().map(tr -> {

            BankStatementResponse response = new BankStatementResponse();

            if (tr.getOriginUserId().equals(userAccountId)) {

                Account accountTarget = accountRepository.findById(tr.getTargetUserId()).orElse(null);

                response.setTitle("Transferido");

                response.setAmount(tr.getAmount());

                response.setCpfFromAccount(FormatCpfResponse.formatCpfResponse(accountTarget.getUsers().getCpf()));

                response.setNameFromAccount(accountTarget.getUsers().getName());

                response.setDate(tr.getDate());
            }
            else {

                Account accountOrigin = accountRepository.findById(tr.getOriginUserId()).orElse(null);

                response.setTitle("Recebido");

                response.setAmount(tr.getAmount());

                response.setCpfFromAccount(FormatCpfResponse.formatCpfResponse(accountOrigin.getUsers().getCpf()));

                response.setNameFromAccount(accountOrigin.getUsers().getName());

                response.setDate(tr.getDate());
            }

            return response;

        }).toList();
    }

}

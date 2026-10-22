package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.BankStatementResponse;
import api.bank.bankapi.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("User/bank_statement")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @GetMapping
    @Operation(summary = "Get bank_statement")
    public ResponseEntity<List<BankStatementResponse>> getBankStatement(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(transferService.getBankStatement(token), HttpStatus.OK);
    }

}

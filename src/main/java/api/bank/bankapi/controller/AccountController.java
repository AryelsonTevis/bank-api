package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.AccountUserResponse;
import api.bank.bankapi.DTO.TargetTransferAccountPost;
import api.bank.bankapi.DTO.TargetTransferAmountAndTargetAccountPost;
import api.bank.bankapi.DTO.UserResponse;
import api.bank.bankapi.domain.user.Users;
import api.bank.bankapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PatchMapping(path = "/transfer")
    @Operation(summary = "Check the amount to be transfer")
    public ResponseEntity<UserResponse> verifyTransferAmount(@AuthenticationPrincipal Users user, @Valid @RequestBody TargetTransferAmountAndTargetAccountPost transferAmountPost) {
        return new ResponseEntity<>(accountService.responseToTransferAttempt(user,transferAmountPost), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Check the transfer information")
    public ResponseEntity<AccountUserResponse> verifyTransferInformation(@AuthenticationPrincipal Users user, @Valid @RequestBody TargetTransferAccountPost transferAccountPost) {
        return new ResponseEntity<>(accountService.getTargetInfo(user,transferAccountPost), HttpStatus.OK);
    }

}

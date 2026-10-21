package api.bank.bankapi.util;

import api.bank.bankapi.domain.account.Account;

import java.math.BigDecimal;

public class AccountCreator {
    public static Account createAccount() {
        return Account.builder().balance(BigDecimal.ZERO).creditLimit(new BigDecimal("50.00")).build();
    }
}

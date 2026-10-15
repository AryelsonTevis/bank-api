package api.bank.bankapi.util;

import api.bank.bankapi.domain.Account;

public class AccountCreator {
    public static Account createAccount() {
        return Account.builder().balance(0).creditLimit(50.00).build();
    }
}

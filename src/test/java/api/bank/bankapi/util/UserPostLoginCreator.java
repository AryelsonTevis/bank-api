package api.bank.bankapi.util;

import api.bank.bankapi.DTO.UserLoginRequest;

public class UserPostLoginCreator {
    public static UserLoginRequest createUserPostLoginRequest(){
        return UserLoginRequest.builder()
                .cpf(UserCreator.createUserToBeSaved().getCpf())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }
}

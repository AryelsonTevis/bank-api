package api.bank.bankapi.util;

import api.bank.bankapi.DTO.UserLoginPostRequest;

public class UserPostLoginCreator {
    public static UserLoginPostRequest createUserPostLoginRequest(){
        return UserLoginPostRequest.builder()
                .cpf(UserCreator.createUserToBeSaved().getCpf())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .build();
    }
}

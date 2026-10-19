package api.bank.bankapi.util;

import api.bank.bankapi.DTO.UserPostRequest;

public class UserPostCreator {
    public static UserPostRequest createUserPostCreator(){
        return UserPostRequest.builder()
                .cpf(UserCreator.createUserToBeSaved().getCpf())
                .birthDate(String.valueOf(UserCreator.createUserToBeSaved().getDateBirth()))
                .email(UserCreator.createUserToBeSaved().getEmail())
                .name(UserCreator.createUserToBeSaved().getName())
                .password(UserCreator.createUserToBeSaved().getPassword())
                .phone(UserCreator.createUserToBeSaved().getPhone())
                .build();
    }
    public static UserPostRequest createUserPostCreatorForIntegration(){
        return UserPostRequest.builder()
                .cpf(UserCreator.createUserToBeSavedForIntegration().getCpf())
                .birthDate("01/06/2000")
                .email(UserCreator.createUserToBeSavedForIntegration().getEmail())
                .name(UserCreator.createUserToBeSavedForIntegration().getName())
                .password(UserCreator.createUserToBeSavedForIntegration().getPassword())
                .phone(UserCreator.createUserToBeSavedForIntegration().getPhone())
                .build();
    }
}

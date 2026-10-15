package api.bank.bankapi.util;

import api.bank.bankapi.DTO.LoginUserResponse;

public class LoginUserResponseCreator {
    public static LoginUserResponse createLoginUserResponse(){
        return LoginUserResponse.builder()
                .name(UserCreator.createUserToBeSaved().getName())
                .cpf(UserCreator.createUserToBeSaved().getCpf()
                        .replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4")
                        .replaceFirst("\\d{3}", "***")
                                .replaceAll("-\\d{2}", "-**"))
                .balance("R$ 0,00")
                .credit("R$ 50,00")
                .build();
    }
}

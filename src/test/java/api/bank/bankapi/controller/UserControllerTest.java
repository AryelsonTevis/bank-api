package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.UserResponse;
import api.bank.bankapi.DTO.UserLoginPostRequest;
import api.bank.bankapi.DTO.UserPostRequest;
import api.bank.bankapi.service.UserService;
import api.bank.bankapi.util.UserResponseCreator;
import api.bank.bankapi.util.UserPostCreator;
import api.bank.bankapi.util.UserPostLoginCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

@Log4j2
@DisplayName("Tests for User Controller")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest {


    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(userServiceMock.saveUser(ArgumentMatchers.any(UserPostRequest.class)))
                .thenReturn(UserResponseCreator.createUserResponse());

        BDDMockito.when(userServiceMock.login(ArgumentMatchers.any(UserLoginPostRequest.class)))
                .thenReturn(UserResponseCreator.createUserResponse());
    }

    @Test
    @DisplayName("Return user response when create successful")
    void create_ReturnUserResponse_WhenCreateSuccessful(){
        UserPostRequest userToBeSaved = UserPostCreator.createUserPostCreator();
        UserResponse user = userController.createUser(userToBeSaved).getBody();

        Assertions.assertThat(user).isNotNull().isEqualTo(UserResponseCreator.createUserResponse());
    }

    @Test
    @DisplayName("Return login user response when login successful")
    void create_ReturnUserResponse_WhenLoginSuccessful(){
        UserPostRequest userToBeSaved = UserPostCreator.createUserPostCreator();
        UserResponse user = userController.createUser(userToBeSaved).getBody();

        Assertions.assertThat(user).isNotNull().isEqualTo(UserResponseCreator.createUserResponse());

        ResponseEntity<UserResponse> loginResponse = userController.login(UserPostLoginCreator.createUserPostLoginRequest());

        Assertions.assertThat(loginResponse.getBody()).isNotNull();
        Assertions.assertThat(loginResponse.getBody().getCpf()).isEqualTo("***.456.789-**");
        Assertions.assertThat(loginResponse.getBody().getBalance()).isEqualTo("R$ 0,00");
        Assertions.assertThat(loginResponse.getBody().getCredit()).isEqualTo("R$ 50,00");
        Assertions.assertThat(loginResponse.getBody().getName()).isEqualTo(user.getName());
    }

}
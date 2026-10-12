package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.UserPostRequest;
import api.bank.bankapi.domain.Users;
import api.bank.bankapi.service.UserService;
import api.bank.bankapi.util.UserCreator;
import api.bank.bankapi.util.UserPostCreator;
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

import java.time.format.DateTimeFormatter;

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
                .thenReturn(UserCreator.createValidUser());
    }

    @Test
    @DisplayName("Return user when successful")
    void create_ReturnUser_WhenSuccessful(){
        UserPostRequest userToBeSaved = UserPostCreator.createUserPostCreator();
        Users user = userController.createUser(userToBeSaved).getBody();

        Assertions.assertThat(user).isNotNull().isEqualTo(UserCreator.createValidUser());
    }

}
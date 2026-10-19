package api.bank.bankapi.integration;

import api.bank.bankapi.DTO.UserPostRequest;
import api.bank.bankapi.DTO.UserResponse;
import api.bank.bankapi.domain.Users;
import api.bank.bankapi.repository.UserRepository;
import api.bank.bankapi.util.UserCreator;
import api.bank.bankapi.util.UserPostCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

@Log4j2
@DisplayName("Teste user controller integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;

    @TestConfiguration
    @Lazy
    static class Config{
        @Bean(name = "testRestTemplate")
        public TestRestTemplate testRestTemplate(@Value("${local.server.port}") int port){
            RestTemplateBuilder restTemplate = new RestTemplateBuilder().baseUri("http://localhost:" + port);
            return new TestRestTemplate(restTemplate);
        }
    }

    @Test
    @DisplayName("Save Returns user when successful")
    void save_ReturnsUser_WhenSuccessful(){
        UserPostRequest userPostRequest = UserPostCreator.createUserPostCreatorForIntegration();

        userPostRequest.setBirthDate("31/02/2000");

        ResponseEntity<UserResponse> usersResponseEntity = testRestTemplate.postForEntity("/User", userPostRequest, UserResponse.class);

        Assertions.assertThat(usersResponseEntity).isNotNull();

        Assertions.assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(usersResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usersResponseEntity.getBody().getName()).isNotNull();
    }
}

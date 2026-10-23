package api.bank.bankapi.security;

import api.bank.bankapi.domain.user.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Log4j2
@Service
public class TokenService {

    @Value("${API_SECURITY_TOKEN_SECRET}")
    private String secret;

    @Value("${PERMISSION_USER_ADMIN}")
    private String permissionUserAdmin;

    public String generateToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("BankAPI")
                    .withSubject(user.getCpf())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("BankAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant genExpirationDate(){

        return LocalDateTime.now().plusMinutes(20).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean chekPermissionToCreateUserAdmin(String permission){

        return permission.equals(permissionUserAdmin);
    }
}

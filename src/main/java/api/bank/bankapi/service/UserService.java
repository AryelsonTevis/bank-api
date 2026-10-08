package api.bank.bankapi.service;

import api.bank.bankapi.DTO.UserCreatePost;
import api.bank.bankapi.domain.User;
import api.bank.bankapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(UserCreatePost userCreatePost) {
        String CpfToBeSaved = formatCpf(userCreatePost.getCpf());
        String phoneToBeSaved = formatPhone(userCreatePost.getPhone());

        LocalDate dateBirth = LocalDate.parse(userCreatePost.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String passwordEntered = userCreatePost.getPassword();

        User userToBeSaved = User.builder()
                .cpf(CpfToBeSaved)
                .name(userCreatePost.getName())
                .password(passwordEncoder.encode(passwordEntered))
                .email(userCreatePost.getEmail())
                .date_birth(Date.valueOf(dateBirth))
                .date_joined(Date.valueOf(LocalDate.now()))
                .phone(phoneToBeSaved).active(true).build();

        log.info("User saved as: " + userToBeSaved);
        return userToBeSaved;
    }

    public boolean passwordMatch(String passwordEntered, String passwordSaved) {
        return passwordEncoder.matches(passwordEntered, passwordSaved);
    }

    public static String formatCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatPhone(String tel) {
        if (tel.length() == 11) {
            return tel.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        return tel.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }

}

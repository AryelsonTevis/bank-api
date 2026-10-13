package api.bank.bankapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", unique = true ,nullable = false)
    @NotBlank(message = "The user cpf cannot be empty")
    private String cpf;

    @Column(name = "name",nullable = false)
    @NotBlank(message = "The user name cannot be empty")
    private String name;

    @Column(name = "password",nullable = false)
    @NotBlank(message = "The user password cannot be empty")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "The user email cannot be empty")
    private String email;

    @Column(name = "date_birth", nullable = false)
    @NotNull(message = "The user date of birth cannot be empty")
    @Past(message = "The birth date must be in the past tense")
    private Date date_birth;

    private Date date_joined;

    @Column(name = "phone", unique = true, nullable = false)
    @NotBlank(message = "The user phone number cannot be empty")
    private String phone;

    private boolean active;
}

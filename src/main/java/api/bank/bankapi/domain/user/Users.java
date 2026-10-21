package api.bank.bankapi.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Users implements UserDetails {
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
    private Date dateBirth;

    private Date dateJoined;

    @Column(name = "phone", unique = true, nullable = false)
    @NotBlank(message = "The user phone number cannot be empty")
    private String phone;

    private boolean active;

    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

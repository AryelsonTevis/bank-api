package api.bank.bankapi.domain.user;

public enum UserRole {
    ADMIN("ADMIN"),

    USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}

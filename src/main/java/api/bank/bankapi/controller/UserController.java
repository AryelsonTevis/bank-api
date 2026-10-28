package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.*;
import api.bank.bankapi.domain.user.Users;
import api.bank.bankapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<AccountUserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserRegisterRequest userCreatePost) {
        userService.saveUser(userCreatePost);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Login User")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginPost) {
        return new ResponseEntity<>(userService.login(userLoginPost), HttpStatus.OK);
    }

    @PatchMapping(path = "/status")
    @Operation(summary = "Disable or enable User")
    public ResponseEntity<UserLoginResponse> disableUser(@AuthenticationPrincipal Users user, @RequestBody StatusRequest newStatus) {
        userService.statusUser(user, newStatus);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/admins")
    @Operation(summary = "Create User Role Admin")
    public ResponseEntity<HttpStatus> createUserAdmin(@RequestHeader("Permission") String password ,@Valid @RequestBody UserRegisterRequest userCreatePost) {
        userService.saveUserAdmin(userCreatePost, password);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

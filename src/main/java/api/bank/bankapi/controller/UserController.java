package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.*;
import api.bank.bankapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<AccountUserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
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
}

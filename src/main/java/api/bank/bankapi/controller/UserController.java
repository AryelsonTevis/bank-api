package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.*;
import api.bank.bankapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("User")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserPostRequest userCreatePost) {
    return new ResponseEntity<>(userService.saveUser(userCreatePost), HttpStatus.CREATED);
    }
    @PostMapping(path = "/login")
    @Operation(summary = "Login User")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginPostRequest userLoginPost) {
        return new ResponseEntity<>(userService.login(userLoginPost), HttpStatus.OK);
    }
}

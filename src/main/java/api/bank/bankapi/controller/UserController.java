package api.bank.bankapi.controller;

import api.bank.bankapi.DTO.UserPostRequest;
import api.bank.bankapi.domain.Users;
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
    public ResponseEntity<Users> createUser(@Valid @RequestBody UserPostRequest userCreatePost) {
    return new ResponseEntity<>(userService.saveUser(userCreatePost), HttpStatus.CREATED);
    }

}

package com.rodrigoramos.investmentaggregator.controller;

import com.rodrigoramos.investmentaggregator.controller.dto.CreateAccountDto;
import com.rodrigoramos.investmentaggregator.controller.dto.CreateUserDto;
import com.rodrigoramos.investmentaggregator.controller.dto.UpdateUserDto;
import com.rodrigoramos.investmentaggregator.entity.User;
import com.rodrigoramos.investmentaggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.findUserById(userId);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users" + userId.toString())).build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                           @RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateById(@PathVariable("userId") String userId,
                                           @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}

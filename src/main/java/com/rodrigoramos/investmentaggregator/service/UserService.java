package com.rodrigoramos.investmentaggregator.service;

import com.rodrigoramos.investmentaggregator.controller.dto.CreateAccountDto;
import com.rodrigoramos.investmentaggregator.entity.Account;
import com.rodrigoramos.investmentaggregator.entity.BillingAddress;
import com.rodrigoramos.investmentaggregator.repository.AccountRepository;
import com.rodrigoramos.investmentaggregator.repository.BillingAddressRepository;
import com.rodrigoramos.investmentaggregator.repository.UserRepository;
import com.rodrigoramos.investmentaggregator.controller.dto.CreateUserDto;
import com.rodrigoramos.investmentaggregator.controller.dto.UpdateUserDto;
import com.rodrigoramos.investmentaggregator.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public Optional<User> findUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if(userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
            UUID.randomUUID(),
            createAccountDto.description(),
            user,
            null,
            new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
            accountCreated.getAccountId(),
            createAccountDto.street(),
            createAccountDto.number(),
            account
        );

        billingAddressRepository.save(billingAddress);
    }
}

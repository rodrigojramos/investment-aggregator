package com.rodrigoramos.investmentaggregator.service;

import com.rodrigoramos.investmentaggregator.UserRepository;
import com.rodrigoramos.investmentaggregator.controller.CreateUserDto;
import com.rodrigoramos.investmentaggregator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

}

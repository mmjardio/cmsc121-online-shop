package com.MP_121.service;

import com.MP_121.model.UsersModel;
import com.MP_121.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UsersRepository usersRepository;

    public Optional<UsersModel> authenticate(String email, String password) {
        return usersRepository.findByEmailAndPassword(email, password);
    }
}

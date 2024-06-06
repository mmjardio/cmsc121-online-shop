package com.MP_121.repository;

import com.MP_121.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UsersRepository extends JpaRepository<UsersModel, Long>{

    Optional <UsersModel> findByEmailAndPassword(String email, String password);
}

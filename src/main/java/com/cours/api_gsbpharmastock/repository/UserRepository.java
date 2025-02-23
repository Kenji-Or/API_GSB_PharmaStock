package com.cours.api_gsbpharmastock.repository;

import com.cours.api_gsbpharmastock.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByMail(String mail);
}

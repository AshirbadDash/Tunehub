package com.io.tunehub.repository;

import com.io.tunehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);
}
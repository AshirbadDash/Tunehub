package com.kodnest.projecttunehub.repository;

import com.kodnest.projecttunehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository interface for interacting with User data in the database.
 * This interface extends JpaRepository which provides JPA related methods
 * like save(), findOne(), findAll(), count(), delete() etc.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a User by their email.
     *
     * @param email The email of the User.
     * @return The User if found, null otherwise.
     */
    User findByEmail(String email);

    /**
     * Finds a User by their password.
     * Note: This method might not be secure and generally it's not a good practice
     * to find users by their password. Consider removing this method if it's not necessary.
     *
     * @param password The password of the User.
     * @return The User if found, null otherwise.
     */
    User findByPassword(String password);
}
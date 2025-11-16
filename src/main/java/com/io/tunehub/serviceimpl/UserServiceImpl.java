package com.io.tunehub.serviceimpl;

import com.io.tunehub.dto.CreateUserDTO;
import com.io.tunehub.entity.AccountType;
import com.io.tunehub.entity.Gender;
import com.io.tunehub.entity.Role;
import com.io.tunehub.entity.User;
import com.io.tunehub.repository.UserRepository;
import com.io.tunehub.service.MusicService;
import com.io.tunehub.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private MusicService musicService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Autowired(required = false)
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }


    @Override
    public User addUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setEmail(createUserDTO.email());
        user.setUsername(createUserDTO.username());
        user.setHashedPassword(BCrypt.hashpw(createUserDTO.password(), BCrypt.gensalt(12)));
        user.setGender(Gender.valueOf(createUserDTO.gender().toUpperCase()));
        user.setAddress(createUserDTO.address());
        user.setRole(Role.valueOf(createUserDTO.role().toUpperCase()));
        user.setAccountType(AccountType.BASIC);

        try {
            if (createUserDTO.profileImageFile() != null && !createUserDTO.profileImageFile().isEmpty()) {
                byte[] imageBytes = createUserDTO.profileImageFile().getBytes();
                user.setProfilePictureData(imageBytes);


                log.info("Received profile image. Size: {} bytes, Filename: {}, Content type: {}",
                        imageBytes.length,
                        createUserDTO.profileImageFile().getOriginalFilename(),
                        createUserDTO.profileImageFile().getContentType());
            } else {
                log.info("No profile image provided for user: {}", createUserDTO.username());
            }

        } catch (IOException e) {
            log.error("Failed to read profile image for user: {}", createUserDTO.username(), e);
            throw new RuntimeException(e);
        }

        User savedUser = userRepository.save(user);


        log.info("User saved successfully. ID: {}, Username: {}, HasImage: {}",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getProfilePictureData() != null);

        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
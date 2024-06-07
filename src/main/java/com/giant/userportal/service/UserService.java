package com.giant.userportal.service;

import com.giant.userportal.constants.UserPortalConstants;
import com.giant.userportal.model.Profile;
import com.giant.userportal.model.User;
import com.giant.userportal.model.Role;
import com.giant.userportal.repository.UserRepository;
import com.giant.userportal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String UPLOAD_PATH = "src/main/resources/static/media/profile-pictures";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user) {

        boolean isSaved = false;

        Role role = roleRepository.getByRoleName(UserPortalConstants.USER_ROLE);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        if (user.getUserId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    @Transactional
    public User updateUser(User user, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return userRepository.save(user);
        }

        Path uploadDir = Paths.get(UPLOAD_PATH + "/" + user.getUserId());
        createOrCleanDirectory(uploadDir);

        String fileName = file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        Files.write(filePath, file.getBytes());

        user.setProfileImagePath(user.getUserId() + "/" + fileName);
        return userRepository.save(user);
    }

    public User getUserById(int userid) {

        Optional<User> optionalUser =  userRepository.findById(userid);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (user.getRole().getRoleName().equals(UserPortalConstants.USER_ROLE)) {
                users.add(user);
            }
        }
        return users;
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public Profile getUserProfile(int userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            Profile profile = new Profile();
            profile.setFirstName(user.getFirstName());
            profile.setFathersName(user.getFathersName());
            profile.setLastName(user.getLastName());
            profile.setPhoneNumber(user.getPhoneNumber());
            profile.setBirthday(user.getBirthday());
            profile.setEmail(user.getEmail());

            if (user.getProfileImagePath() != null) {
                profile.setProfileImagePath(user.getProfileImagePath());
            } else {
                profile.setProfileImagePath("default-profile-picture.jpeg");
            }

            return profile;
        } else {
            throw new RuntimeException();
        }
    }

    private void createOrCleanDirectory(Path directory) throws IOException {

        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        } else {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path path : stream) {
                    Files.delete(path);
                }
            }
        }
    }
}

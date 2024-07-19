package com.med.acadmys.services;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.dtos.UserEntityDto;
import com.med.acadmys.models.User;
import com.med.acadmys.repositories.UserRepository;
import com.med.acadmys.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResponseUtil responseUtil;
    private final ModelMapper modelMapper;

    public UserDetailsService userDetailsService() {
        return username ->
                userRepository.findByEmail(username).orElseThrow(() ->
                        new UsernameNotFoundException("Failed to authenticate user with the given email"));
    }

    public Optional<Response> getUserByUserId(String userId) {
        Response response;
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            log.info("User exists fetching user details...");
            Type type = new TypeToken<UserEntityDto>() {
            }.getType();
            UserEntityDto mappedUser = modelMapper.map(user.get(), type);
            response = responseUtil.generateSuccessResponse(mappedUser);
        } else {
            response = responseUtil.generateFailureResponse("Fail to find user against the given userId");
        }

        return Optional.of(response);
    }

    public Optional<Response> getAllUsers() {
        Response response;
        List<User> allUsers = userRepository.findAll();
        if (!allUsers.isEmpty()) {
            Type type = new TypeToken<List<UserEntityDto>>() {
            }.getType();
            List<UserEntityDto> users = modelMapper.map(allUsers, type);
            response = responseUtil.generateSuccessResponse(users);
        } else {
            log.info("Failed to fetch user details, no user found");
            response = responseUtil.generateFailureResponse("No User found");
        }
        return Optional.of(response);
    }

    public Optional<Response> getUser(String email) {
        Response response;
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Type type = new TypeToken<UserEntityDto>() {
            }.getType();
            UserEntityDto fetchedUser = modelMapper.map(user, type);
            response = responseUtil.generateSuccessResponse(fetchedUser);
        } else {
            response = responseUtil.generateFailureResponse("Failed to fetch user detail using email: " + email);
        }
        return Optional.of(response);
    }

    public Optional<Response> updatePartialUpdate(String userId, Map<String, Object> updates) {
        Response response;
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            applyUpdates(user.get(), updates);
            response = responseUtil.generateSuccessResponse("User details successfully updated");
        } else {
            response = responseUtil.generateFailureResponse("Failed to find user details against userId: " + userId);
        }
        return Optional.of(response);
    }

    private void applyUpdates(User user, Map<String, Object> updates) {
        if (updates.containsKey("isEnabled")) {
            user.setIsEnabled((Boolean) updates.get("isEnabled"));
            userRepository.save(user);
        }
    }
}
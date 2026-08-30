package com.example.exceptionhandling.service;

import com.example.exceptionhandling.dto.CreateUserRequest;
import com.example.exceptionhandling.dto.UserResponse;
import com.example.exceptionhandling.exception.DuplicateResourceException;
import com.example.exceptionhandling.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, UserResponse> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public UserService() {
        // Seed initial demo data
        createUserInternal("Alice Smith", "alice@example.com", 28);
        createUserInternal("Bob Johnson", "bob@example.com", 34);
    }

    private UserResponse createUserInternal(String name, String email, Integer age) {
        long id = idCounter.incrementAndGet();
        UserResponse user = new UserResponse(id, name, email, age);
        userStore.put(id, user);
        return user;
    }

    public List<UserResponse> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    public UserResponse getUserById(Long id) {
        UserResponse user = userStore.get(id);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " does not exist");
        }
        return user;
    }

    public UserResponse createUser(CreateUserRequest request) {
        boolean emailExists = userStore.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(request.getEmail()));

        if (emailExists) {
            throw new DuplicateResourceException(
                    "User with email '" + request.getEmail() + "' already exists");
        }

        return createUserInternal(request.getName(), request.getEmail(), request.getAge());
    }
}

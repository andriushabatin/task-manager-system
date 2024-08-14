package com.andriusha.task.management.system.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: User not found"));
    }
}

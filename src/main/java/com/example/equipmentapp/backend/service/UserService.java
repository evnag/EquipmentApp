package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.User;
import com.example.equipmentapp.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }

    public long count() {
        return userRepository.count();
    }

    public void save(User user) {
        if (user == null) {
            return;
        }
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}

package com.expense.ExpenseTracker.service;

import com.expense.ExpenseTracker.exception.NotFoundException;
import com.expense.ExpenseTracker.exception.UsernameAlreadyExistsException;
import com.expense.ExpenseTracker.model.User;
import com.expense.ExpenseTracker.repository.RoleRepository;
import com.expense.ExpenseTracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password) {
        repository.findByUsername(username)
                .ifPresent(existingExpenseGroup -> {throw new UsernameAlreadyExistsException(username);});
        User newUser = new User(username, passwordEncoder.encode(password), roleRepository.findByName("ROLE_USER"));
        return repository.save(newUser);
    }

    public User getById(UUID userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}

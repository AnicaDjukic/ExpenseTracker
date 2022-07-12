package com.expense.ExpenseTracker.service;

import com.expense.ExpenseTracker.dto.ExpenseGroupRequestDto;
import com.expense.ExpenseTracker.exception.AccessResourceDeniedException;
import com.expense.ExpenseTracker.exception.NameAlreadyExistsException;
import com.expense.ExpenseTracker.exception.NotFoundException;
import com.expense.ExpenseTracker.model.ExpenseGroup;
import com.expense.ExpenseTracker.model.User;
import com.expense.ExpenseTracker.repository.ExpenseGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseGroupService {

    private final ExpenseGroupRepository repository;

    private final UserService userService;

    public ExpenseGroupService(ExpenseGroupRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public ExpenseGroup addNew(ExpenseGroup expenseGroup, String username) {
        User user = userService.getByUsername(username);
        repository.findByNameAndUser(expenseGroup.getName(), user)
                .ifPresent(expGroup -> {throw new NameAlreadyExistsException(ExpenseGroup.class.getSimpleName(), expenseGroup.getName());});
        expenseGroup.setUser(user);
        return repository.save(expenseGroup);
    }

    public Page<ExpenseGroup> getAll(int pageNo, int size, String username) {
        User user = userService.getByUsername(username);
        return repository.findByUser(user, PageRequest.of(pageNo, size));
    }

    public ExpenseGroup update(UUID id, ExpenseGroupRequestDto updateDto, String username) throws NotFoundException {
        Optional<ExpenseGroup> existingExpGroup = findByNameAndUserUsername(updateDto.getName(), username);
        if (existingExpGroup.isPresent() && !existingExpGroup.get().getId().equals(id))
            throw new NameAlreadyExistsException(ExpenseGroup.class.getSimpleName(), updateDto.getName());
        ExpenseGroup expenseGroup = getByIdAndUserUsername(id, username);
        expenseGroup.setName(updateDto.getName());
        expenseGroup.setDescription(updateDto.getDescription());
        return repository.save(expenseGroup);
    }

    public void deleteById(UUID id, String username) throws NotFoundException, SQLException {
        ExpenseGroup expenseGroup = getByIdAndUserUsername(id, username);
        repository.delete(expenseGroup);
    }

    private Optional<ExpenseGroup> findByNameAndUserUsername(String name, String username) {
        User user = userService.getByUsername(username);
        return repository.findByNameAndUser(name, user);
    }

    public ExpenseGroup getByIdAndUserUsername(UUID id, String username) {
        User user = userService.getByUsername(username);
        repository.findById(id).orElseThrow(() -> new NotFoundException(ExpenseGroup.class.getSimpleName()));
        return repository.findByIdAndUser(id, user).orElseThrow(() -> new AccessResourceDeniedException(ExpenseGroup.class.getSimpleName()));
    }
}

package com.expense.ExpenseTracker.controller;

import com.expense.ExpenseTracker.dto.ExpenseGroupRequestDto;
import com.expense.ExpenseTracker.dto.ExpenseGroupResponseDto;
import com.expense.ExpenseTracker.dto.IncomeGroupRequestDto;
import com.expense.ExpenseTracker.dto.IncomeGroupResponseDto;
import com.expense.ExpenseTracker.exception.NotFoundException;
import com.expense.ExpenseTracker.model.IncomeGroup;
import com.expense.ExpenseTracker.service.IncomeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/income-groups")
public class IncomeGroupController {

    private final IncomeGroupService incomeGroupService;

    public IncomeGroupController(IncomeGroupService incomeGroupService) {
        this.incomeGroupService = incomeGroupService;
    }

    @PostMapping
    public ResponseEntity<IncomeGroupResponseDto> create(@RequestBody @Valid IncomeGroupRequestDto newExpenseGroupDto) {
        IncomeGroup expenseGroup = new IncomeGroup(newExpenseGroupDto.getName(), newExpenseGroupDto.getDescription());
        IncomeGroup savedIncomeGroup = incomeGroupService.addNew(expenseGroup);
        return new ResponseEntity(new IncomeGroupResponseDto(savedIncomeGroup.getId(), savedIncomeGroup.getName(), savedIncomeGroup.getDescription()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncomeGroupResponseDto>> getAll() {
        List<IncomeGroup> incomeGroups = incomeGroupService.getAll();
        List<IncomeGroupResponseDto> incomeGroupDtos = new ArrayList<>();
        for(IncomeGroup expenseGroup : incomeGroups) {
            incomeGroupDtos.add(new IncomeGroupResponseDto(expenseGroup.getId(), expenseGroup.getName(), expenseGroup.getDescription()));
        }
        return ResponseEntity.ok(incomeGroupDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<IncomeGroupResponseDto> getById(@PathVariable Long id) throws NotFoundException {
        IncomeGroup incomeGroup = incomeGroupService.getById(id);
        return ResponseEntity.ok(new IncomeGroupResponseDto(incomeGroup.getId(), incomeGroup.getName(), incomeGroup.getDescription()));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExpenseGroupResponseDto> update(@PathVariable Long id, @RequestBody @Valid ExpenseGroupRequestDto updateDto) throws NotFoundException {
        IncomeGroup updatedIncomeGroup = incomeGroupService.update(id, updateDto);
        return ResponseEntity.ok(new ExpenseGroupResponseDto(updatedIncomeGroup.getId(), updatedIncomeGroup.getName(), updatedIncomeGroup.getDescription()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) throws NotFoundException {
        incomeGroupService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

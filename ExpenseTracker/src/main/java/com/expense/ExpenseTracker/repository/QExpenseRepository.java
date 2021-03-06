package com.expense.ExpenseTracker.repository;

import com.expense.ExpenseTracker.model.QExpense;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class QExpenseRepository {

    private final EntityManager entityManager;

    public QExpenseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<QExpense> getLastFew(int size, String username) {
        QExpense expense = QExpense.expense;
        JPAQuery<QExpense> query = new JPAQuery<>(entityManager);
        query.from(expense).where(expense.user.username.eq(username)).orderBy(expense.creationTime.desc()).limit(size);
        return query.fetch();
    }

    public List<QExpense> getLastFewByExpenseGroupId(UUID expenseGroupId, int size) {
        QExpense expense = QExpense.expense;
        JPAQuery<QExpense> query = new JPAQuery<>(entityManager);
        query.from(expense).where(expense.expenseGroup.id.eq(expenseGroupId)).orderBy(expense.creationTime.desc()).limit(size);
        return query.fetch();
    }

    public List<QExpense> getExpensesForYesterday(String username) {
        QExpense expense = QExpense.expense;
        JPAQuery<QExpense> query = new JPAQuery<>(entityManager);
        query.from(expense).where(expense.creationTime.between(LocalDateTime.now().minusDays(1), LocalDateTime.now())
                .and(expense.user.username.eq(username))).orderBy(expense.creationTime.desc());
        return query.fetch();
    }
}

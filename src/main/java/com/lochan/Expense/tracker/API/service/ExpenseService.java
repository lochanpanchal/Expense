package com.lochan.Expense.tracker.API.service;

import com.lochan.Expense.tracker.API.model.Expense;
import com.lochan.Expense.tracker.API.model.User;
import com.lochan.Expense.tracker.API.repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    IExpenseRepo iExpenseRepo;


    public String createExpense(Expense expense, User user) {
        expense.setUser(user);
        iExpenseRepo.save(expense);

        return "A New Post Uploaded by "+ expense;
    }
}

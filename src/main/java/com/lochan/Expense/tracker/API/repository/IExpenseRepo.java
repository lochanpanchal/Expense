package com.lochan.Expense.tracker.API.repository;

import com.lochan.Expense.tracker.API.model.Expense;
import com.lochan.Expense.tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepo extends JpaRepository<Expense,Integer> {

}

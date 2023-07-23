package com.lochan.Expense.tracker.API.repository;


import com.lochan.Expense.tracker.API.model.AuthenticationToken;
import com.lochan.Expense.tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}

package com.lochan.Expense.tracker.API.controller;

import com.lochan.Expense.tracker.API.model.Expense;
import com.lochan.Expense.tracker.API.model.User;
import com.lochan.Expense.tracker.API.model.dto.SignInInput;
import com.lochan.Expense.tracker.API.model.dto.SignUpOutput;
import com.lochan.Expense.tracker.API.service.AuthenticationService;
import com.lochan.Expense.tracker.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    //sign up, sign in , sign out a particular instagram user
    @PostMapping("user/signup")
    public SignUpOutput signUpInstaUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInInstaUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }


    @PostMapping("new/expense")
    public String createExpense(@RequestBody @Valid Expense expense,@RequestParam String userEmail,@RequestParam String userToken)
    {
        boolean checkSignInUser = authenticationService.checkSignInUser(userEmail,userToken);
        if(checkSignInUser)
        {
            return userService.createExpense(expense,userEmail);
        }
        else{
            return "You are not a Authenticated User";
        }
    }







}

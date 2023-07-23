package com.lochan.Expense.tracker.API.service;

import com.lochan.Expense.tracker.API.model.AuthenticationToken;
import com.lochan.Expense.tracker.API.model.Expense;
import com.lochan.Expense.tracker.API.model.User;
import com.lochan.Expense.tracker.API.model.dto.SignInInput;
import com.lochan.Expense.tracker.API.model.dto.SignUpOutput;
import com.lochan.Expense.tracker.API.repository.IUserRepo;
import com.lochan.Expense.tracker.API.service.emailUtility.EmailHandler;
import com.lochan.Expense.tracker.API.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ExpenseService expenseService;


    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

            //hash the password: encrypt the password
            try {
                String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

                //saveAppointment the user with the new encrypted password

                user.setUserPassword(encryptedPassword);
                iUserRepo.save(user);

                return new SignUpOutput(signUpStatus, "User registered successfully!!!");
            } catch (Exception e) {
                signUpStatusMessage = "Internal error occurred during sign up";
                signUpStatus = false;
                return new SignUpOutput(signUpStatus, signUpStatusMessage);
            }
        }

    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);
                String toEmail = signInInput.getEmail();
                EmailHandler.sendEmail(toEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String createExpense(Expense expense, String userEmail) {
        User user = iUserRepo.findFirstByUserEmail(userEmail);
        return expenseService.createExpense(expense,user);
    }
}

package com.lochan.Expense.tracker.API.service;


import com.lochan.Expense.tracker.API.model.AuthenticationToken;
import com.lochan.Expense.tracker.API.model.User;
import com.lochan.Expense.tracker.API.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo authenticationRepo;

    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);
    }

    public void saveAuthToken(AuthenticationToken authToken)
    {
        authenticationRepo.save(authToken);
    }

    public AuthenticationToken findFirstByUser(User user) {
        return authenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        authenticationRepo.delete(token);
    }

    public boolean checkSignInUser(String userEmail, String userToken) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(userToken);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();
        return tokenConnectedEmail.equals(userEmail);
    }
}

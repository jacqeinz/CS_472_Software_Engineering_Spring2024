package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import spring2024.cs472.hotelwebsite.services.AccountService;

public class SignUpGuest {
    @Autowired private AccountService accountService;

    @GetMapping(/)
}

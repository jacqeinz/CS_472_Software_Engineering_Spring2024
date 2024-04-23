package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.Account;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Admin> getAllAdmins() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().filter(a -> a instanceof Admin).map(a -> (Admin)a).collect(Collectors.toList());
    }

    public Account validateLogin(String username, String password) {
        for(Account account : getAllAccounts()) {
            if(account.getUserName().equals(username) && account.getUserPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}

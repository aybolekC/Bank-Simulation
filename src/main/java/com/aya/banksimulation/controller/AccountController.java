package com.aya.banksimulation.controller;
import com.aya.banksimulation.enums.AccountType;
import com.aya.banksimulation.model.Account;
import com.aya.banksimulation.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/index")
    public String accountList(Model model){

        model.addAttribute("accountList", accountService.listAllAccount());

        return "account/index";
    }


    @GetMapping("/create-form")
    public String getCreateForm(Model model){

        model.addAttribute("account", Account.builder().build());
        model.addAttribute("accountTypes", AccountType.values());


        return "/account/create-account";
    }

    @PostMapping("/create")
    public String postCreateForm(@Valid @ModelAttribute("account") Account account , BindingResult bindingResult, Model model ){

        if(bindingResult.hasErrors()){

            model.addAttribute("accountTypes", AccountType.values());
            return "/account/create-account";

        }

        accountService.createNewAcount(account.getBalance(),
                new Date(),
                account.getAccountType(),
                account.getUserId(), account.getAccountStatus());

        model.addAttribute("accountList", accountService.listAllAccount());

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")UUID id, Model model){

        accountService.deleteAccount(id);

        return "redirect:/index";
    }
}

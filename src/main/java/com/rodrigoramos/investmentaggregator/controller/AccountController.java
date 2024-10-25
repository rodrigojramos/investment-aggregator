package com.rodrigoramos.investmentaggregator.controller;

import com.rodrigoramos.investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.rodrigoramos.investmentaggregator.controller.dto.CreateAccountDto;
import com.rodrigoramos.investmentaggregator.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto) {
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }
}

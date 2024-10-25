package com.rodrigoramos.investmentaggregator.service;

import com.rodrigoramos.investmentaggregator.client.BrapiClient;
import com.rodrigoramos.investmentaggregator.controller.dto.AccountStockResponseDto;
import com.rodrigoramos.investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.rodrigoramos.investmentaggregator.entity.AccountStock;
import com.rodrigoramos.investmentaggregator.entity.AccountStockId;
import com.rodrigoramos.investmentaggregator.repository.AccountRepository;
import com.rodrigoramos.investmentaggregator.repository.AccountStockRepository;
import com.rodrigoramos.investmentaggregator.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository  accountStockRepository;

    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository,
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository,
                          BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
            id,
            account,
            stock,
            dto.quantity()
        );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> findStocksByAccountId(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(acc ->
                        new AccountStockResponseDto(
                            acc.getStock().getStockId(),
                            acc.getQuantity(),
                            getTotal(acc.getQuantity(), acc.getStock().getStockId())
                        ))
                .toList();

    }

    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().get(0).regularMarketPrice();

        return quantity * price;
    }
}

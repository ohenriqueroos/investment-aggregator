package com.investment_aggregator.insvestment_aggregator.service;

import com.investment_aggregator.insvestment_aggregator.controller.dto.AccountStockResponseDto;
import com.investment_aggregator.insvestment_aggregator.controller.dto.AssociateAccountStockDto;
import com.investment_aggregator.insvestment_aggregator.entity.AccountStock;
import com.investment_aggregator.insvestment_aggregator.entity.AccountStockId;
import com.investment_aggregator.insvestment_aggregator.repository.AccountRepository;
import com.investment_aggregator.insvestment_aggregator.repository.AccountStockRepository;
import com.investment_aggregator.insvestment_aggregator.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.stockId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> Entity
        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(id, account, stock, dto.quantity());
        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks().stream().map(as -> new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), 0.0)).toList();
    }
}

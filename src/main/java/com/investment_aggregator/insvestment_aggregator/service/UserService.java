package com.investment_aggregator.insvestment_aggregator.service;

import com.investment_aggregator.insvestment_aggregator.controller.dto.AccountResponseDto;
import com.investment_aggregator.insvestment_aggregator.controller.dto.CrateAccountDto;
import com.investment_aggregator.insvestment_aggregator.controller.dto.CreateUserDto;
import com.investment_aggregator.insvestment_aggregator.controller.dto.UpdateUserDto;
import com.investment_aggregator.insvestment_aggregator.entity.Account;
import com.investment_aggregator.insvestment_aggregator.entity.BillingAddress;
import com.investment_aggregator.insvestment_aggregator.entity.User;
import com.investment_aggregator.insvestment_aggregator.repository.AccountRepository;
import com.investment_aggregator.insvestment_aggregator.repository.BillingAddressRepository;
import com.investment_aggregator.insvestment_aggregator.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        // DTO -> Entity;

        var entity = new User(
                null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CrateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(null, createAccountDto.description(), user, null, new ArrayList<>());

        var billingAddress = new BillingAddress(null, account, createAccountDto.street(), createAccountDto.number());
        account.setBillingAddress(billingAddress);

        accountRepository.save(account);
    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts().stream().map(ac -> new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription())).toList();
    }
}

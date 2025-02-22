package com.investment_aggregator.insvestment_aggregator.repository;

import com.investment_aggregator.insvestment_aggregator.entity.AccountStock;
import com.investment_aggregator.insvestment_aggregator.entity.AccountStockId;
import com.investment_aggregator.insvestment_aggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}

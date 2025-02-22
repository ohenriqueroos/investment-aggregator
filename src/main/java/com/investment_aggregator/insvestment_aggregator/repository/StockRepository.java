package com.investment_aggregator.insvestment_aggregator.repository;

import com.investment_aggregator.insvestment_aggregator.entity.Stock;
import com.investment_aggregator.insvestment_aggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}

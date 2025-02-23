package com.investment_aggregator.insvestment_aggregator.service;

import com.investment_aggregator.insvestment_aggregator.controller.dto.CreateStockDto;
import com.investment_aggregator.insvestment_aggregator.entity.Stock;
import com.investment_aggregator.insvestment_aggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        // DTO -> Entity
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}

package com.rodrigoramos.investmentaggregator.service;

import com.rodrigoramos.investmentaggregator.controller.dto.CreateStockDto;
import com.rodrigoramos.investmentaggregator.entity.Stock;
import com.rodrigoramos.investmentaggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        var stock = new Stock(
            createStockDto.stockId(),
            createStockDto.description()
        );

        stockRepository.save(stock);
    }
}

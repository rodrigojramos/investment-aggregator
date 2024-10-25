package com.rodrigoramos.investmentaggregator.controller;

import com.rodrigoramos.investmentaggregator.controller.dto.CreateStockDto;
import com.rodrigoramos.investmentaggregator.controller.dto.CreateUserDto;
import com.rodrigoramos.investmentaggregator.entity.User;
import com.rodrigoramos.investmentaggregator.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto) {
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}

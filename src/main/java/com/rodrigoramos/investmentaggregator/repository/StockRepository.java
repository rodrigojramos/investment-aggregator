package com.rodrigoramos.investmentaggregator.repository;

import com.rodrigoramos.investmentaggregator.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}

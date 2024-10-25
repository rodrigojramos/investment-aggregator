package com.rodrigoramos.investmentaggregator.repository;

import com.rodrigoramos.investmentaggregator.entity.AccountStock;
import com.rodrigoramos.investmentaggregator.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}

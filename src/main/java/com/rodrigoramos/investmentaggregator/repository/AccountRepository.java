package com.rodrigoramos.investmentaggregator.repository;

import com.rodrigoramos.investmentaggregator.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}

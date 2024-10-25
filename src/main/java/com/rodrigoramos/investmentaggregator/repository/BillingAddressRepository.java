package com.rodrigoramos.investmentaggregator.repository;


import com.rodrigoramos.investmentaggregator.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}

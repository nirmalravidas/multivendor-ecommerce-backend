package com.nirmalravidas.multivendor_ecommerce.repository;

import com.nirmalravidas.multivendor_ecommerce.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(Long sellerId);
}

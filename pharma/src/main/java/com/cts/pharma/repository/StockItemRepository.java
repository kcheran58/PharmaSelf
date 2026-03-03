package com.cts.pharma.repository;

import com.cts.pharma.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {
    List<StockItem> findByStorekeepersAcceptedTrue();
    List<StockItem> findByBillId(Integer billId);
    List<StockItem> findByRequestId(Integer requestId);

    // ✅ For same-batch merge logic
    Optional<StockItem> findByBatchIdAndRequestId(String batchId, Integer requestId);
  }

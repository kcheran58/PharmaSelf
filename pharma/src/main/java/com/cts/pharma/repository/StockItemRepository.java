package com.cts.pharma.repository;

import com.cts.pharma.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {
    List<StockItem> findByStorekeepersAcceptedTrue();
    List<StockItem> findBySupplierId(Integer supplierId);
    List<StockItem> findByProductId(Integer productId);

    // ✅ For same-batch merge logic
    Optional<StockItem> findByBatchIdAndProductId(String batchId, Integer productId);
  }

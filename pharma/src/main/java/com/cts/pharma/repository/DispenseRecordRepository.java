package com.cts.pharma.repository;

import com.cts.pharma.entity.DispenseRecord;
import com.cts.pharma.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DispenseRecordRepository extends JpaRepository<DispenseRecord, Integer> {
    List<DispenseRecord> findByPatientId(String patientId);
    List<DispenseRecord> findByStatus(String status);
    List<DispenseRecord> findByStockItem(StockItem stockItem);
    List<DispenseRecord> findByStockItem_StockId(Integer stockId);
}

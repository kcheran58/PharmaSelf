package com.cts.pharma.serviceImpl;

import com.cts.pharma.dto.DispenseRequest;
import com.cts.pharma.dto.ReturnRequest;
import com.cts.pharma.entity.*;
import com.cts.pharma.repository.*;
import com.cts.pharma.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PharmacistServiceImpl implements PharmacistService {

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private DispenseRecordRepository dispenseRecordRepository;

    @Autowired
    private ReturnRecordRepository returnRecordRepository;

    @Override
    public List<StockItem> getAvailableStock() {
        return stockItemRepository.findByStorekeepersAcceptedTrue();
    }

    @Override
    @Transactional
    public DispenseRecord dispense(DispenseRequest req) {
        StockItem stock = stockItemRepository.findById(req.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock not found: " + req.getStockId()));

        if (stock.getAvailableQuantity() < req.getQuantityDispensed()) {
            throw new RuntimeException(
                "Insufficient stock. Available: " + stock.getAvailableQuantity()
            );
        }

        stock.setAvailableQuantity(
            stock.getAvailableQuantity() - req.getQuantityDispensed()
        );
        stockItemRepository.save(stock);

        DispenseRecord record = new DispenseRecord();
        record.setStockItem(stock);                      // ✅ JPA relationship
        record.setProductId(stock.getProductId());
        record.setProductName(stock.getProductName());
        record.setBatchId(stock.getBatchId());
        record.setPatientName(req.getPatientName());
        record.setPatientId(req.getPatientId());
        record.setPrescriptionRef(req.getPrescriptionRef());
        record.setQuantityDispensed(req.getQuantityDispensed());
        record.setDispenseDate(LocalDate.now());
        record.setDispensedBy(req.getDispensedBy());
        record.setStatus("DISPENSED");

        return dispenseRecordRepository.save(record);
    }

    @Override
    @Transactional
    public ReturnRecord returnMedicine(ReturnRequest req) {
        DispenseRecord dispense = dispenseRecordRepository.findById(req.getDispenseId())
                .orElseThrow(() -> new RuntimeException(
                    "Dispense record not found: " + req.getDispenseId()
                ));

        if (req.getQuantityReturned() > dispense.getQuantityDispensed()) {
            throw new RuntimeException("Return quantity exceeds dispensed quantity.");
        }

        // ✅ LAZY loads stockItem — @Transactional keeps session open
        StockItem stock = dispense.getStockItem();
        stock.setAvailableQuantity(
            stock.getAvailableQuantity() + req.getQuantityReturned()
        );
        stockItemRepository.save(stock);

        dispense.setStatus("RETURNED");
        dispenseRecordRepository.save(dispense);

        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setDispenseRecord(dispense);        // ✅ JPA relationship
        returnRecord.setProductId(dispense.getProductId());
        returnRecord.setProductName(dispense.getProductName());
        returnRecord.setBatchId(dispense.getBatchId());
        returnRecord.setPatientId(dispense.getPatientId());
        returnRecord.setQuantityReturned(req.getQuantityReturned());
        returnRecord.setReturnReason(req.getReturnReason());
        returnRecord.setReturnDate(LocalDate.now());
        returnRecord.setReturnedBy(req.getReturnedBy());
        returnRecord.setRestocked(true);

        return returnRecordRepository.save(returnRecord);
    }

    @Override
    public List<DispenseRecord> getAllDispenseRecords() {
        return dispenseRecordRepository.findAll();
    }

    @Override
    public List<DispenseRecord> getDispenseByPatient(String patientId) {
        return dispenseRecordRepository.findByPatientId(patientId);
    }

    @Override
    public List<ReturnRecord> getAllReturnRecords() {
        return returnRecordRepository.findAll();
    }
}

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
    

    //Get the availablestocks
    @Override
    public List<StockItem> getAvailableStock() {
        return stockItemRepository.findByStorekeepersAcceptedTrue();
    }
    
    //Dispense new record 
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
        record.setStockItem(stock);                      // JPA relationship
        record.setRequestId(stock.getRequestId());
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
     

    //return the dispensed item
    @Override
    @Transactional
    public ReturnRecord returnMedicine(ReturnRequest req) {
        //Get the dispensed record details 
        DispenseRecord dispense = dispenseRecordRepository.findById(req.getDispenseId())
                .orElseThrow(() -> new RuntimeException(
                    "Dispense record not found: " + req.getDispenseId()//if record not available it throws the runtime exception with message record not found
                ));
        

        //if dispensed quantity < returned quantity then it considered as fake medicines we did'nt dispensed that medicines
        if (req.getQuantityReturned() > dispense.getQuantityDispensed()) {
            throw new RuntimeException("Return quantity exceeds dispensed quantity.");
        }

        //LAZY loads stockItem — @Transactional keeps session open
        StockItem stock = dispense.getStockItem();
        stock.setAvailableQuantity(
            stock.getAvailableQuantity() + req.getQuantityReturned()
        );
        stockItemRepository.save(stock);

        dispense.setStatus("RETURNED");
        dispenseRecordRepository.save(dispense);
        

        //Insert new return record details in the return-record table
        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setDispenseRecord(dispense);        //JPA relationship
        returnRecord.setRequestId(dispense.getRequestId());
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


    //get all dispensed record
    @Override
    public List<DispenseRecord> getAllDispenseRecords() {
        return dispenseRecordRepository.findAll();
    }
    

    //get dispense record by patient Id
    @Override
    public List<DispenseRecord> getDispenseByPatient(String patientId) {
        return dispenseRecordRepository.findByPatientId(patientId);
    } 
    

    //get all return records in the table
    @Override
    public List<ReturnRecord> getAllReturnRecords() {
        return returnRecordRepository.findAll();
    }
}

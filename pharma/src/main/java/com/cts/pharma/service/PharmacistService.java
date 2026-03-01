package com.cts.pharma.service;

import com.cts.pharma.dto.DispenseRequest;
import com.cts.pharma.dto.ReturnRequest;
import com.cts.pharma.entity.DispenseRecord;
import com.cts.pharma.entity.ReturnRecord;
import com.cts.pharma.entity.StockItem;
import java.util.List;

public interface PharmacistService {
    List<StockItem> getAvailableStock();
    DispenseRecord dispense(DispenseRequest request);
    ReturnRecord returnMedicine(ReturnRequest request);
    List<DispenseRecord> getAllDispenseRecords();
    List<DispenseRecord> getDispenseByPatient(String patientId);
    List<ReturnRecord> getAllReturnRecords();
}

package com.cts.pharma.controller;

import com.cts.pharma.dto.DispenseRequest;
import com.cts.pharma.dto.ReturnRequest;
import com.cts.pharma.entity.DispenseRecord;
import com.cts.pharma.entity.ReturnRecord;
import com.cts.pharma.entity.StockItem;
import com.cts.pharma.service.PharmacistService;      // ✅ inject INTERFACE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pharmacist")
@CrossOrigin("*")
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;       // ✅ Spring injects PharmacistServiceImpl

    // GET /api/pharmacist/stock
    @GetMapping("/stock")
    public List<StockItem> getAvailableStock() {
        return pharmacistService.getAvailableStock();
    }

    // POST /api/pharmacist/dispense
    @PostMapping("/dispense")
    public DispenseRecord dispense(@RequestBody DispenseRequest request) {
        return pharmacistService.dispense(request);
    }

    // POST /api/pharmacist/return
    @PostMapping("/return")
    public ReturnRecord returnMedicine(@RequestBody ReturnRequest request) {
        return pharmacistService.returnMedicine(request);
    }

    // GET /api/pharmacist/dispense-records
    @GetMapping("/dispense-records")
    public List<DispenseRecord> getAllDispenseRecords() {
        return pharmacistService.getAllDispenseRecords();
    }

    // GET /api/pharmacist/dispense-records/patient/{patientId}
    @GetMapping("/dispense-records/patient/{patientId}")
    public List<DispenseRecord> getByPatient(@PathVariable String patientId) {
        return pharmacistService.getDispenseByPatient(patientId);
    }

    // GET /api/pharmacist/return-records
    @GetMapping("/return-records")
    public List<ReturnRecord> getAllReturnRecords() {
        return pharmacistService.getAllReturnRecords();
    }
}


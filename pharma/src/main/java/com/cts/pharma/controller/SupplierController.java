package com.cts.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.entity.Supplier;
import com.cts.pharma.service.SupplierService;

@RestController
@RequestMapping("/api/supplier")
@CrossOrigin("*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // 🔹 Get only admin approved products
    @GetMapping("/approved-products")
    public List<ProcurementProduct> getApprovedProducts() {
        return supplierService.getApprovedProducts();
    }

    // 🔹 Submit supplier details
    @PostMapping("/submit")
    public Supplier submitDetails(@RequestBody Supplier supplier) {
        return supplierService.submitSupplierDetails(supplier);
    }
}
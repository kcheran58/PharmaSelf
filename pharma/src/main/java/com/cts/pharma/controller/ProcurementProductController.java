package com.cts.pharma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.repository.ProcurementProductRepository;

// New request from PO to Admin 
@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProcurementProductController {

    @Autowired
    private ProcurementProductRepository repository;

    @PostMapping
    public ProcurementProduct addProduct(@RequestBody ProcurementProduct product) {
        return repository.save(product);
    }

    @GetMapping
    public List<ProcurementProduct> getAllProducts() {
        return repository.findAll();
    }
}

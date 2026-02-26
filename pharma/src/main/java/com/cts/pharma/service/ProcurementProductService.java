package com.cts.pharma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.repository.ProcurementProductRepository;

@Service
public class ProcurementProductService {

    @Autowired
    private ProcurementProductRepository repository;

    // Add Product
    public ProcurementProduct addProduct(ProcurementProduct product) {

        // Set default status values
        product.setAdminStatus(false);
        product.setSupplierStatus(false);

        return repository.save(product);
    }

    // Get All Products
    public List<ProcurementProduct> getAllProducts() {
        return repository.findAll();
    }
}
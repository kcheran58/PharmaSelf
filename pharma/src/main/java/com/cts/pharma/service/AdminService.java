package com.cts.pharma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.repository.ProcurementProductRepository;

@Service
public class AdminService {

    @Autowired
    private ProcurementProductRepository repository;

    public ProcurementProduct updateAdminStatus(Integer productId, boolean status) {

        ProcurementProduct product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Only updating admin status
        product.setAdminStatus(status);

        return repository.save(product);
    }
}
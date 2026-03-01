package com.cts.pharma.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.entity.Supplier;
import com.cts.pharma.repository.ProcurementProductRepository;
import com.cts.pharma.repository.SupplierRepository;
import com.cts.pharma.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private ProcurementProductRepository procurementRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // 1. View Admin Approved Products
    @Override
    public List<ProcurementProduct> getApprovedProducts() {
        return procurementRepository.findByAdminStatusTrue();
    }

    //2. Supplier fills details
    @Override
    public Supplier submitSupplierDetails(Supplier supplier) {

        // Find procurement product
        ProcurementProduct product = procurementRepository
                .findById(supplier.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Save supplier details
        supplier.setSupplierStatus(true);
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Update procurement product supplier status
        product.setSupplierStatus(true);
        procurementRepository.save(product);

        return savedSupplier;
    }
}
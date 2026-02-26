package com.cts.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pharma.entity.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    // 🔹 Optional: Find supplier details by productId
    List<Supplier> findByProductId(Integer productId);

    // 🔹 Optional: Get only confirmed supplier records
    List<Supplier> findBySupplierStatusTrue();

}
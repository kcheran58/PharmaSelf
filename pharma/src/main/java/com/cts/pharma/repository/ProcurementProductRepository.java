package com.cts.pharma.repository;


import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.pharma.entity.ProcurementProduct;

public interface ProcurementProductRepository 
    extends JpaRepository<ProcurementProduct, Integer> {
        List<ProcurementProduct> findByAdminStatusTrue();
}

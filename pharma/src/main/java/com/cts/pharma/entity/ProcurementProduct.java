package com.cts.pharma.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String sku;
    private String productName;
    private String genericName;
    private String formulation;
    private String strength;
    private Integer requiredQuantity;
    private String storageCondition;
    private boolean adminStatus;
    private boolean supplierStatus;
}
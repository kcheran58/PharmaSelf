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
    private int requestId;              // 1, 2, 3, 4 ...
    private String sku;                 // "SKU-1001", "MED-2045", "PHA-3021"
    private String productName;         // "Paracetamol", "Amoxicillin", "Ibuprofen"
    private String genericName;         // "Acetaminophen", "Amoxicillin Trihydrate"
    private String formulation;         // "Tablet", "Capsule", "Syrup", "Injection"
    private String strength;            // "500mg", "250mg/5ml", "100mg/2ml"
    private int requiredQuantity;       // 100, 500, 1000
    private String storageCondition;    // "Room Temperature", "Refrigerate 2-8°C", "Keep Dry"
    private boolean adminStatus;         // "PENDING", "APPROVED", "REJECTED"
    private boolean supplierStatus;      // "PENDING", "APPROVED", "REJECTED"

}
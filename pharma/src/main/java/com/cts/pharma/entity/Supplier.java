package com.cts.pharma.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;   // ✅ PRIMARY KEY

    private Integer productId;

    private String batchId;

    private String productName;
    private String genericName;
    private String manufacturer;

    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    private Integer quantity;

    private Double amountPerQuantity;
    private Double totalAmount;

    private boolean supplierStatus;
}
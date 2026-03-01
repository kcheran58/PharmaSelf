package com.cts.pharma.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_item")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;

    private Integer supplierId;       // logical FK to Supplier.billId
    private Integer productId;        // logical FK to ProcurementProduct

    private String sku;
    private String productName;
    private String genericName;
    private String batchId;
    private String manufacturer;

    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    private Integer totalQuantity;
    private Integer availableQuantity;

    private Double amountPerQuantity;
    private String storageCondition;

    private boolean storekeepersAccepted;
    private LocalDate acceptedDate;

    // JPA Relationship: One StockItem → Many DispenseRecords
    @ToString.Exclude
    @OneToMany(
        mappedBy = "stockItem",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JsonManagedReference("stock-dispense")
    private List<DispenseRecord> dispenseRecords;
}

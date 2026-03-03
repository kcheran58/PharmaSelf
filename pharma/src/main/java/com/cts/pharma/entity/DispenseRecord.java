package com.cts.pharma.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispense_record")
public class DispenseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dispenseId;

    private int requestId;
    private String productName;
    private String batchId;
    private String patientName;
    private String patientId;
    private String prescriptionRef;
    private int quantityDispensed;
    private LocalDate dispenseDate;
    private String dispensedBy;
    private String status; // DISPENSED | RETURNED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId", nullable = false)
    @JsonBackReference("stock-dispense")
    private StockItem stockItem;

    @ToString.Exclude
    @OneToOne(
        mappedBy = "dispenseRecord",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JsonManagedReference("dispense-return")
    private ReturnRecord returnRecord;

    public Integer getStockId() {
        return stockItem != null ? stockItem.getStockId() : null;
    }
}


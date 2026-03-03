package com.cts.pharma.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "return_record")
public class ReturnRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer returnId;

    private int requestId;
    private String productName;
    private String batchId;
    private String patientId;
    private int  quantityReturned;
    private String returnReason;
    private LocalDate returnDate;
    private String returnedBy;
    private boolean restocked;

    // JPA: One ReturnRecord → One DispenseRecord
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispenseId", nullable = false)
    @JsonBackReference("dispense-return")
    private DispenseRecord dispenseRecord;

    // Convenience getter
    public Integer getDispenseId() {
        return dispenseRecord != null ? dispenseRecord.getDispenseId() : null;
    }
}



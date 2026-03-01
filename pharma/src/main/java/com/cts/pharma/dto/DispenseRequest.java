package com.cts.pharma.dto;
import lombok.Data;

@Data
public class DispenseRequest {
    private Integer stockId;
    private String patientName;
    private String patientId;
    private String prescriptionRef;
    private Integer quantityDispensed;
    private String dispensedBy;
}

package com.cts.pharma.dto;
import lombok.Data;

@Data
public class ReturnRequest {
    private Integer dispenseId;
    private Integer quantityReturned;
    private String returnReason;
    private String returnedBy;
}

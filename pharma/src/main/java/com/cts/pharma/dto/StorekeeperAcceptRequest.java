package com.cts.pharma.dto;
import lombok.Data;

@Data
public class StorekeeperAcceptRequest {
    private boolean accepted;
    private String storekeeperId;
}

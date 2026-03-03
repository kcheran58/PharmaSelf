package com.cts.pharma.service;

import com.cts.pharma.entity.ProcurementProduct;

public interface AdminService {

    ProcurementProduct updateAdminStatus(Integer requestID, boolean status);
}
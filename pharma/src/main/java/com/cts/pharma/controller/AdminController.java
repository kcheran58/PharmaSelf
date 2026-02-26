package com.cts.pharma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cts.pharma.dto.AdminStatusUpdateRequest;
import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/update-admin-status/{id}")
    public ProcurementProduct updateAdminStatus(
            @PathVariable Integer id,
            @RequestBody AdminStatusUpdateRequest request) {

        return adminService.updateAdminStatus(id, request.isAdminStatus());
    }
}
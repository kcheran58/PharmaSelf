package com.cts.pharma.controller;

import com.cts.pharma.dto.StorekeeperAcceptRequest;
import com.cts.pharma.entity.StockItem;
import com.cts.pharma.entity.Supplier;
import com.cts.pharma.service.StorekeeperService;     // ✅ inject INTERFACE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/storekeeper")
@CrossOrigin("*")
public class StorekeeperController {

    @Autowired
    private StorekeeperService storekeeperService;    

    // GET /api/storekeeper/pending-bills
    @GetMapping("/pending-bills")
    public List<Supplier> getPendingBills() {
        return storekeeperService.getPendingSupplierSubmissions();
    }

    // PUT /api/storekeeper/accept/{supplierId}
    @PutMapping("/accept/{supplierId}")
    public StockItem acceptBill(
            @PathVariable Integer supplierId,
            @RequestBody StorekeeperAcceptRequest request) {
        return storekeeperService.acceptSupplierBill(supplierId, request.isAccepted());
    }

    // GET /api/storekeeper/stock
    @GetMapping("/stock")
    public List<StockItem> getAllStock() {
        return storekeeperService.getAllStock();
    }

    // GET /api/storekeeper/stock/product/{productId}
    @GetMapping("/stock/product/{productId}")
    public List<StockItem> getStockByProduct(@PathVariable Integer productId) {
        return storekeeperService.getStockByProduct(productId);
    }
}

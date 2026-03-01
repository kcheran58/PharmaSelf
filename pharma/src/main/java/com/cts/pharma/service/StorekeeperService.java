package com.cts.pharma.service;

import com.cts.pharma.entity.StockItem;
import com.cts.pharma.entity.Supplier;
import java.util.List;

public interface StorekeeperService {
    List<Supplier> getPendingSupplierSubmissions();
    StockItem acceptSupplierBill(Integer supplierId, boolean accepted);
    List<StockItem> getAllStock();
    List<StockItem> getStockByProduct(Integer productId);
}



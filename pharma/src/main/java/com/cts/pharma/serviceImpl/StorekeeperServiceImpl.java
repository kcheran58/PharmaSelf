package com.cts.pharma.serviceImpl;

import com.cts.pharma.entity.ProcurementProduct;
import com.cts.pharma.entity.StockItem;
import com.cts.pharma.entity.Supplier;
import com.cts.pharma.repository.ProcurementProductRepository;
import com.cts.pharma.repository.StockItemRepository;
import com.cts.pharma.repository.SupplierRepository;
import com.cts.pharma.service.StorekeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StorekeeperServiceImpl implements StorekeeperService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private ProcurementProductRepository productRepository; 
    @Override
    public List<Supplier> getPendingSupplierSubmissions() {
        return supplierRepository.findBySupplierStatusTrue();
    }


    @Override
    public StockItem acceptSupplierBill(Integer supplierId, boolean accepted) {

     Supplier supplier = supplierRepository.findById(supplierId)
             .orElseThrow(() -> new RuntimeException("Supplier bill not found: " + supplierId));
 
     if (!accepted) {
         throw new RuntimeException("StoreKeeper rejected bill: " + supplierId);
     }
 
     // Fetch storageCondition from original ProcurementProduct
     ProcurementProduct product = productRepository.findById(supplier.getProductId())
             .orElseThrow(() -> new RuntimeException("Product not found: " + supplier.getProductId()));
 
     Optional<StockItem> existingBatch = stockItemRepository
             .findByBatchIdAndProductId(supplier.getBatchId(), supplier.getProductId());
 
     if (existingBatch.isPresent()) {
         // MERGE — same batch
         StockItem stock = existingBatch.get();
         stock.setTotalQuantity(stock.getTotalQuantity() + supplier.getQuantity());
         stock.setAvailableQuantity(stock.getAvailableQuantity() + supplier.getQuantity());
         stock.setSupplierId(supplier.getBillId());
         return stockItemRepository.save(stock);
 
     } 
     else {
         // CREATE — new batch
         StockItem stock = new StockItem();
         stock.setSupplierId(supplier.getBillId());
         stock.setProductId(supplier.getProductId());
         stock.setSku(product.getSku());                          //from product
         stock.setProductName(supplier.getProductName());
         stock.setGenericName(supplier.getGenericName());
         stock.setBatchId(supplier.getBatchId());
         stock.setManufacturer(supplier.getManufacturer());
         stock.setManufactureDate(supplier.getManufactureDate());
         stock.setExpiryDate(supplier.getExpiryDate());
         stock.setTotalQuantity(supplier.getQuantity());
         stock.setAvailableQuantity(supplier.getQuantity());
         stock.setAmountPerQuantity(supplier.getAmountPerQuantity());
         stock.setStorageCondition(product.getStorageCondition()); // from product
         stock.setStorekeepersAccepted(true);
         stock.setAcceptedDate(LocalDate.now());
         return stockItemRepository.save(stock);
       }
    }


    @Override
    public List<StockItem> getAllStock() {
        return stockItemRepository.findByStorekeepersAcceptedTrue();
    }

    @Override
    public List<StockItem> getStockByProduct(Integer productId) {
        return stockItemRepository.findByProductId(productId);
    }
}




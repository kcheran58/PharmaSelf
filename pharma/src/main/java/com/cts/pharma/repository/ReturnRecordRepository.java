package com.cts.pharma.repository;

import com.cts.pharma.entity.ReturnRecord;
import com.cts.pharma.entity.DispenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReturnRecordRepository extends JpaRepository<ReturnRecord, Integer> {
    List<ReturnRecord> findByPatientId(String patientId);
    ReturnRecord findByDispenseRecord(DispenseRecord dispenseRecord);
    ReturnRecord findByDispenseRecord_DispenseId(Integer dispenseId);
}

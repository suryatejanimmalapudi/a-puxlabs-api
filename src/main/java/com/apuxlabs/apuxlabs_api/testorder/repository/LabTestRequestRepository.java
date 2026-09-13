package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LabTestRequestRepository extends JpaRepository<LabTestRequest, Long> {

    // Fetches the pending queue for the technician UI
    @Query("""
        SELECT req FROM LabTestRequest req 
        JOIN FETCH req.labOrder ord 
        JOIN FETCH ord.registration reg 
        JOIN FETCH req.testMaster tm 
        WHERE req.status = :status 
        ORDER BY req.priority DESC, ord.orderDate ASC
    """)
    List<LabTestRequest> findPendingWorklist(TestStatus status);

    Optional<LabTestRequest> findByBarcode(String barcode);
}
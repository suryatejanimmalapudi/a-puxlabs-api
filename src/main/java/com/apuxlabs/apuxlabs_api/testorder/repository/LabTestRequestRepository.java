package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Query("""
        SELECT req FROM LabTestRequest req
        JOIN FETCH req.labOrder ord
        JOIN FETCH ord.registration reg
        JOIN FETCH req.testMaster tm
        WHERE req.status IN :statuses AND (:status IS NULL OR req.status = :status)
        ORDER BY req.completedAt DESC
    """)
    List<LabTestRequest> findReports(List<TestStatus> statuses);

    @Query("""
        SELECT req FROM LabTestRequest req
        JOIN FETCH req.labOrder ord
        JOIN FETCH ord.registration reg
        JOIN FETCH req.testMaster tm
        WHERE reg.id = :patientId AND req.status IN :statuses
        ORDER BY req.completedAt DESC
    """)
    List<LabTestRequest> findPatientReports(Long patientId, List<TestStatus> statuses);

    Optional<LabTestRequest> findByBarcode(String barcode);

    // Auto-generates: SELECT * FROM lab_test_requests WHERE status != ?
    List<LabTestRequest> findByStatusNot(TestStatus status);

    @Query("""
        SELECT req FROM LabTestRequest req JOIN req.labOrder ord JOIN ord.registration reg JOIN req.testMaster tm
        WHERE req.status <> :completed AND (:status IS NULL OR req.status = :status)
        AND (:search = '' OR LOWER(reg.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(COALESCE(reg.lastName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(reg.barcode, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(tm.name) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(tm.code) LIKE LOWER(CONCAT('%', :search, '%')) OR CAST(reg.id AS string) LIKE CONCAT('%', :search, '%'))
        ORDER BY req.priority DESC, ord.orderDate ASC
        """)
    Page<LabTestRequest> searchCollections(@Param("completed") TestStatus completed, @Param("status") TestStatus status, @Param("search") String search, Pageable pageable);

    @Query("""
        SELECT req FROM LabTestRequest req JOIN req.labOrder ord JOIN ord.registration reg JOIN req.testMaster tm
        WHERE req.status = :status
        AND (:search = '' OR LOWER(reg.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(COALESCE(reg.lastName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(reg.barcode, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(tm.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(tm.code) LIKE LOWER(CONCAT('%', :search, '%')) OR CAST(reg.id AS string) LIKE CONCAT('%', :search, '%'))
        AND (:department = '' OR LOWER(tm.department) = LOWER(:department))
        AND (:priority IS NULL OR req.priority = :priority)
        ORDER BY req.priority DESC, req.collectedAt ASC
        """)
    Page<LabTestRequest> searchPendingWorklist(@Param("status") TestStatus status, @Param("search") String search, @Param("department") String department, @Param("priority") com.apuxlabs.apuxlabs_api.testorder.enums.TestPriority priority, Pageable pageable);

    @Query("""
        SELECT req FROM LabTestRequest req JOIN req.labOrder ord JOIN ord.registration reg JOIN req.testMaster tm
        WHERE req.status IN :statuses
        AND (:search = '' OR LOWER(reg.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(COALESCE(reg.lastName, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(COALESCE(reg.barcode, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(tm.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(tm.code) LIKE LOWER(CONCAT('%', :search, '%')) OR CAST(reg.id AS string) LIKE CONCAT('%', :search, '%'))
        ORDER BY req.completedAt DESC
        """)
    Page<LabTestRequest> searchReports(@Param("statuses") List<TestStatus> statuses, @Param("status") TestStatus status, @Param("search") String search, Pageable pageable);
}

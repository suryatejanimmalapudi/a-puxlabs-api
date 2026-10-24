package com.apuxlabs.apuxlabs_api.registration.repository;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, Long>
{
    Optional<Registration> findByBarcode(String barcode);

    Optional<Registration> findByReportDownloadToken(UUID reportDownloadToken);

    @Query(value = """
        SELECT DISTINCT r FROM Registration r
        LEFT JOIN r.labOrders o
        LEFT JOIN o.testRequests tr
        LEFT JOIN tr.testPackage p
        WHERE (:search = '' OR LOWER(r.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.lastName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.phone, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.barcode, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.uhid, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR CAST(r.id AS string) LIKE CONCAT('%', :search, '%'))
        AND r.registrationDate >= :fromDate
        AND r.registrationDate < :toDate
        AND (:packageName = '' OR LOWER(COALESCE(p.name, '')) = LOWER(:packageName))
        ORDER BY r.registrationDate DESC
        """, countQuery = """
        SELECT COUNT(DISTINCT r) FROM Registration r
        LEFT JOIN r.labOrders o
        LEFT JOIN o.testRequests tr
        LEFT JOIN tr.testPackage p
        WHERE (:search = '' OR LOWER(r.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.lastName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.phone, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.barcode, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(COALESCE(r.uhid, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          OR CAST(r.id AS string) LIKE CONCAT('%', :search, '%'))
        AND r.registrationDate >= :fromDate
        AND r.registrationDate < :toDate
        AND (:packageName = '' OR LOWER(COALESCE(p.name, '')) = LOWER(:packageName))
        """)
    Page<Registration> searchRegistrations(@Param("search") String search, @Param("fromDate") LocalDateTime fromDate,
                                            @Param("toDate") LocalDateTime toDate, @Param("packageName") String packageName,
                                            Pageable pageable);
}

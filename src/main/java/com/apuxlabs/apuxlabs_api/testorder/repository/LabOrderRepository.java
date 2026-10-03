package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    List<LabOrder> findByRegistrationIdOrderByOrderDateAsc(Long registrationId);
}

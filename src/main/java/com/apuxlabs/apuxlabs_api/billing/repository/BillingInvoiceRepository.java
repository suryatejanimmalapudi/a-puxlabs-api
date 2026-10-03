package com.apuxlabs.apuxlabs_api.billing.repository;
import com.apuxlabs.apuxlabs_api.billing.entity.BillingInvoice; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BillingInvoiceRepository extends JpaRepository<BillingInvoice,Long> {
 List<BillingInvoice> findAllByOrderByCreatedAtDescIdDesc();
 List<BillingInvoice> findByRegistrationIdOrderByCreatedAtAscIdAsc(Long registrationId);
}

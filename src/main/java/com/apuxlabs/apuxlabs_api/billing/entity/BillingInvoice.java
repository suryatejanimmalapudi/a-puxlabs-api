package com.apuxlabs.apuxlabs_api.billing.entity;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.ArrayList; import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity @Table(name="billing_invoice")
public class BillingInvoice {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="invoice_number", nullable=false, unique=true) private String invoiceNumber;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="registration_id") private Registration registration;
 @Column(nullable=false) private BigDecimal discount=BigDecimal.ZERO;
 @Column(name="tax_rate", nullable=false) private BigDecimal taxRate=BigDecimal.ZERO;
 @Column(name="amount_paid", nullable=false) private BigDecimal amountPaid=BigDecimal.ZERO;
 @Column(name="payment_method") private String paymentMethod;
 @Column(name="created_at", nullable=false) private LocalDate createdAt;
 @Column(name="due_date", nullable=false) private LocalDate dueDate;
 @OneToMany(mappedBy="invoice", cascade=CascadeType.ALL, orphanRemoval=true) private List<BillingInvoiceItem> items=new ArrayList<>();
}

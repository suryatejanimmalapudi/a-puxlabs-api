package com.apuxlabs.apuxlabs_api.billing.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter; import java.math.BigDecimal;
@Getter @Setter @NoArgsConstructor @Entity @Table(name="billing_invoice_item")
public class BillingInvoiceItem { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="invoice_id",nullable=false) private BillingInvoice invoice; @Column(name="item_code",nullable=false) private String code; @Column(name="item_name",nullable=false) private String name; @Column(name="unit_price",nullable=false) private BigDecimal price; @Column(nullable=false) private Integer quantity=1; }

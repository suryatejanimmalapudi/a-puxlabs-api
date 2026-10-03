package com.apuxlabs.apuxlabs_api.billing.dto;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.List;
public final class BillingDtos { private BillingDtos(){}
 public record Item(String id,String name,String code,BigDecimal price,Integer qty){}
 public record Create(Long patientId,List<Item> items,BigDecimal discount,BigDecimal taxRate,BigDecimal amountPaid,String paymentMethod,LocalDate dueDate){}
 public record Payment(BigDecimal amount,String paymentMethod){}
 public record Response(Long id,String invoiceNumber,Long patientId,String patientName,Integer age,String gender,List<Item> items,BigDecimal discount,BigDecimal taxRate,BigDecimal amountPaid,String paymentMethod,LocalDate createdAt,LocalDate dueDate){}
 public record PatientSummary(Long patientId,String patientName,List<Item> items,BigDecimal registrationFee){}
}

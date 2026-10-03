package com.apuxlabs.apuxlabs_api.billing.controller;
import com.apuxlabs.apuxlabs_api.billing.dto.BillingDtos.*; import com.apuxlabs.apuxlabs_api.billing.entity.*; import com.apuxlabs.apuxlabs_api.billing.repository.BillingInvoiceRepository; import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository; import com.apuxlabs.apuxlabs_api.testorder.repository.LabOrderRepository; import jakarta.transaction.Transactional; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.math.BigDecimal; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/v1/billing")
public class BillingController {
 private final BillingInvoiceRepository invoices; private final RegistrationRepository registrations; private final LabOrderRepository orders;
 public BillingController(BillingInvoiceRepository i,RegistrationRepository r,LabOrderRepository o){invoices=i;registrations=r;orders=o;}
 @GetMapping @Transactional public List<Response> all(){
  // Reconcile orders created before automatic order invoicing was enabled.
  orders.findAll().forEach(order -> {
   var patientInvoices = invoices.findByRegistrationIdOrderByCreatedAtAscIdAsc(order.getRegistration().getId());
   if (patientInvoices.isEmpty()) return;
   var target = patientInvoices.get(0);
   order.getTestRequests().forEach(test -> {
    boolean alreadyBilled = patientInvoices.stream().flatMap(i -> i.getItems().stream()).anyMatch(item ->
      item.getCode().equalsIgnoreCase(test.getTestMaster().getCode())
        && item.getPrice().compareTo(test.getPriceCharged()) == 0);
    if (!alreadyBilled) {
     var item = new BillingInvoiceItem();
     item.setInvoice(target);
     item.setCode(test.getTestMaster().getCode());
     item.setName(test.getTestMaster().getName());
     item.setPrice(test.getPriceCharged());
     item.setQuantity(1);
     target.getItems().add(item);
    }
   });
  });
  invoices.saveAll(invoices.findAll());
  return invoices.findAllByOrderByCreatedAtDescIdDesc().stream().map(this::out).toList();
 }
 @GetMapping("/patients/{patientId}/summary") public PatientSummary summary(@PathVariable Long patientId){var p=registrations.findById(patientId).orElseThrow();var items=new ArrayList<Item>();items.add(new Item("registration-fee","Registration fee","REG-FEE",p.getRegistrationAmount(),1));orders.findByRegistrationIdOrderByOrderDateAsc(patientId).forEach(o->o.getTestRequests().forEach(t->items.add(new Item(String.valueOf(t.getId()),t.getTestMaster().getName(),t.getTestMaster().getCode(),t.getPriceCharged(),1))));return new PatientSummary(p.getId(),(p.getFirstName()+" "+(p.getLastName()==null?"":p.getLastName())).trim(),items,p.getRegistrationAmount());}
 @PostMapping @Transactional public Response create(@RequestBody Create c){var p=registrations.findById(c.patientId()).orElseThrow(); if(c.items()==null||c.items().isEmpty()) throw new IllegalArgumentException("At least one invoice item is required"); var x=new BillingInvoice(); x.setRegistration(p); x.setInvoiceNumber("INV-"+LocalDate.now().getYear()+"-"+String.format("%04d",new Random().nextInt(9999)+1)); x.setDiscount(n(c.discount())); x.setTaxRate(n(c.taxRate())); x.setAmountPaid(n(c.amountPaid())); x.setPaymentMethod(c.paymentMethod()); x.setCreatedAt(LocalDate.now()); x.setDueDate(c.dueDate()==null?LocalDate.now().plusDays(30):c.dueDate()); c.items().forEach(i->{var z=new BillingInvoiceItem();z.setInvoice(x);z.setCode(i.code());z.setName(i.name());z.setPrice(i.price());z.setQuantity(i.qty()==null?1:i.qty());x.getItems().add(z);}); return out(invoices.save(x)); }
 @PostMapping("/{id}/payments") @Transactional public Response payment(@PathVariable Long id,@RequestBody Payment p){var x=invoices.findById(id).orElseThrow(); if(p.amount()==null||p.amount().signum()<=0) throw new IllegalArgumentException("Payment amount must be positive"); x.setAmountPaid(x.getAmountPaid().add(p.amount()));x.setPaymentMethod(p.paymentMethod());return out(invoices.save(x));}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){invoices.deleteById(id);return ResponseEntity.noContent().build();}
 private BigDecimal n(BigDecimal x){return x==null?BigDecimal.ZERO:x;}
 private Response out(BillingInvoice x){var p=x.getRegistration();var name=(p.getFirstName()+" "+(p.getLastName()==null?"":p.getLastName())).trim();Integer age=p.getDateOfBirth()==null?0:java.time.Period.between(p.getDateOfBirth(),LocalDate.now()).getYears();var items=new ArrayList<Item>();boolean hasRegistrationFee=x.getItems().stream().anyMatch(i->"REG-FEE".equalsIgnoreCase(i.getCode()));if(!hasRegistrationFee)items.add(new Item("registration-fee","Registration fee","REG-FEE",p.getRegistrationAmount(),1));items.addAll(x.getItems().stream().map(i->new Item(String.valueOf(i.getId()),i.getName(),i.getCode(),i.getPrice(),i.getQuantity())).toList());return new Response(x.getId(),x.getInvoiceNumber(),p.getId(),name,age,p.getGender(),items,x.getDiscount(),x.getTaxRate(),x.getAmountPaid(),x.getPaymentMethod(),x.getCreatedAt(),x.getDueDate());}
}

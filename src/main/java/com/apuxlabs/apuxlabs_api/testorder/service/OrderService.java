package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import com.apuxlabs.apuxlabs_api.billing.entity.BillingInvoice;
import com.apuxlabs.apuxlabs_api.billing.entity.BillingInvoiceItem;
import com.apuxlabs.apuxlabs_api.billing.repository.BillingInvoiceRepository;
import com.apuxlabs.apuxlabs_api.testorder.dto.CreateOrderRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.TestItemRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabOrder;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestMaster;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestPackage;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestPackageItem;
import com.apuxlabs.apuxlabs_api.testorder.entity.LabOrderPackage;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestPriority;
import com.apuxlabs.apuxlabs_api.testorder.enums.TestStatus;
import com.apuxlabs.apuxlabs_api.testorder.repository.LabOrderRepository;
import com.apuxlabs.apuxlabs_api.testorder.repository.TestMasterRepository;
import com.apuxlabs.apuxlabs_api.testorder.repository.TestPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final LabOrderRepository labOrderRepository;
    private final TestMasterRepository testMasterRepository;
    private final RegistrationRepository registrationRepository;
    private final BillingInvoiceRepository billingInvoiceRepository;
    private final TestPackageRepository testPackageRepository;

    @Transactional
    public Long createOrder(CreateOrderRequestDto request) {

        // 1. Get reference to existing patient registration (uses proxy, avoids extra DB select)
        Registration registration = registrationRepository.getReferenceById(request.getRegistrationId());

        LabOrder order = new LabOrder();
        order.setRegistration(registration);
        order.setReferringDoctorName(request.getReferringDoctorName());


        // orderDate is mapped with insertable=false, updatable=false so the DB sets CURRENT_TIMESTAMP,
        // but we can set it here if we want it immediately available in the returned entity.
        order.setOrderDate(LocalDateTime.now());

        // Start gross at ZERO instead of the fee
        BigDecimal totalGross = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        // 2. Process each standalone test requested
        for (int i = 0; i < (request.getTests() == null ? 0 : request.getTests().size()); i++) {
            TestItemRequestDto testReq = request.getTests().get(i);

            TestMaster master = testMasterRepository.findById(testReq.getTestMasterId())
                    .orElseThrow(() -> new RuntimeException("Test Master not found with ID: " + testReq.getTestMasterId()));

            // Secure pricing logic: rely on DB base price, not frontend payload
            BigDecimal basePrice = master.getDefaultPrice();
            BigDecimal itemDiscount = testReq.getDiscountAmount() != null ? testReq.getDiscountAmount() : BigDecimal.ZERO;
            BigDecimal priceCharged = basePrice.subtract(itemDiscount);

            totalGross = totalGross.add(basePrice);
            totalDiscount = totalDiscount.add(itemDiscount);

            LabTestRequest labTest = new LabTestRequest();
            labTest.setLabOrder(order);
            labTest.setTestMaster(master);
            labTest.setBasePrice(basePrice);
            labTest.setDiscountAmount(itemDiscount);
            labTest.setPriceCharged(priceCharged);
            labTest.setStatus(TestStatus.PENDING_COLLECTION);
            labTest.setPriority(TestPriority.Routine); // Can be made dynamic later if needed

            order.getTestRequests().add(labTest);
        }

        // 3. Preserve the selected package for billing and expand it into lab requests.
        for (Long packageId : request.getPackageIds() == null ? java.util.List.<Long>of() : request.getPackageIds()) {
            TestPackage testPackage = testPackageRepository.findById(packageId)
                    .filter(TestPackage::isActive)
                    .orElseThrow(() -> new RuntimeException("Inactive or unknown package: " + packageId));

            LabOrderPackage orderPackage = new LabOrderPackage();
            orderPackage.setLabOrder(order);
            orderPackage.setTestPackage(testPackage);
            orderPackage.setPackageCode(testPackage.getCode());
            orderPackage.setPackageName(testPackage.getName());
            orderPackage.setPackagePrice(testPackage.getPackagePrice());
            order.getPackages().add(orderPackage);
            totalGross = totalGross.add(testPackage.getPackagePrice());

            for (TestPackageItem packageItem : testPackage.getItems()) {
                LabTestRequest labTest = new LabTestRequest();
                labTest.setLabOrder(order);
                labTest.setTestMaster(packageItem.getTestMaster());
                labTest.setTestPackage(testPackage);
                labTest.setBasePrice(packageItem.getPackageTestPrice());
                labTest.setDiscountAmount(BigDecimal.ZERO);
                labTest.setPriceCharged(packageItem.getPackageTestPrice());
                labTest.setStatus(TestStatus.PENDING_COLLECTION);
                labTest.setPriority(TestPriority.Routine);
                order.getTestRequests().add(labTest);
            }
        }

        // 4. Finalize totals
        order.setGrossAmount(totalGross);
        order.setTotalDiscount(totalDiscount);
        order.setNetAmount(totalGross.subtract(totalDiscount));

        // 5. Save order and cascade test requests/packages
        LabOrder savedOrder = labOrderRepository.save(order);

        // Orders created later from Patient Directory must also be visible in Billing.
        // Keep them as a separate invoice so the registration invoice is not modified.
        BillingInvoice invoice = new BillingInvoice();
        invoice.setRegistration(registration);
        invoice.setInvoiceNumber("INV-" + LocalDate.now().getYear() + "-" + String.format("%04d", new Random().nextInt(9999) + 1));
        invoice.setDiscount(BigDecimal.ZERO);
        invoice.setTaxRate(BigDecimal.ZERO);
        invoice.setAmountPaid(BigDecimal.ZERO);
        invoice.setCreatedAt(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        order.getPackages().forEach(pkg -> {
            BillingInvoiceItem item = new BillingInvoiceItem();
            item.setInvoice(invoice);
            item.setCode(pkg.getPackageCode());
            item.setName(pkg.getPackageName());
            item.setPrice(pkg.getPackagePrice());
            item.setQuantity(1);
            invoice.getItems().add(item);
        });
        order.getTestRequests().stream()
                .filter(test -> order.getPackages().stream().noneMatch(pkg -> pkg.getTestPackage().getItems().stream().anyMatch(item -> item.getTestMaster().getId().equals(test.getTestMaster().getId()))))
                .forEach(test -> {
                    BillingInvoiceItem item = new BillingInvoiceItem();
                    item.setInvoice(invoice);
                    item.setCode(test.getTestMaster().getCode());
                    item.setName(test.getTestMaster().getName());
                    item.setPrice(test.getPriceCharged());
                    item.setQuantity(1);
                    invoice.getItems().add(item);
                });
        billingInvoiceRepository.save(invoice);

        return savedOrder.getId();
    }
}

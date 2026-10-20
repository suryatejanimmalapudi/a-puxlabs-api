package com.apuxlabs.apuxlabs_api.registration.service.impl;

import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationRequestDto;
import com.apuxlabs.apuxlabs_api.registration.dto.RegistrationResponseDto;
import com.apuxlabs.apuxlabs_api.registration.entity.Registration;
import com.apuxlabs.apuxlabs_api.registration.entity.RegistrationDispatchMethod;
import com.apuxlabs.apuxlabs_api.registration.mapper.RegistrationMapper;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationDispatchMethodRepository;
import com.apuxlabs.apuxlabs_api.registration.repository.RegistrationRepository;
import com.apuxlabs.apuxlabs_api.registration.service.RegistrationService;
import com.apuxlabs.apuxlabs_api.billing.entity.BillingInvoice;
import com.apuxlabs.apuxlabs_api.billing.entity.BillingInvoiceItem;
import com.apuxlabs.apuxlabs_api.billing.repository.BillingInvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Random;
import java.util.List;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
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
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final RegistrationDispatchMethodRepository registrationDispatchMethodRepository;
    private final BillingInvoiceRepository billingInvoiceRepository;
    private final LabOrderRepository labOrderRepository;
    private final TestMasterRepository testMasterRepository;
    private final TestPackageRepository testPackageRepository;

    public RegistrationServiceImpl(
            RegistrationRepository registrationRepository,
            RegistrationMapper registrationMapper,
            RegistrationDispatchMethodRepository registrationDispatchMethodRepository,
            BillingInvoiceRepository billingInvoiceRepository,
            LabOrderRepository labOrderRepository,
            TestMasterRepository testMasterRepository,
            TestPackageRepository testPackageRepository) {

        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.registrationDispatchMethodRepository = registrationDispatchMethodRepository;
        this.billingInvoiceRepository = billingInvoiceRepository;
        this.labOrderRepository = labOrderRepository;
        this.testMasterRepository = testMasterRepository;
        this.testPackageRepository = testPackageRepository;
    }

    @Override
    @Transactional
    public RegistrationResponseDto createRegistration(RegistrationRequestDto request) {

        Registration registration = registrationMapper.toEntity(request);

        registration.setRegistrationDate(LocalDateTime.now());
        registration.setBarcode("REG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        registration.setStatus("ACTIVE");
        registration.setRegistrationAmount(request.getRegistrationAmount() == null ? BigDecimal.ZERO : request.getRegistrationAmount());
        registration.setCreatedAt(LocalDateTime.now());
        registration.setUpdatedAt(LocalDateTime.now());

        registration.setDispatchMethods(
                registrationMapper.toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                )
        );

        Registration savedRegistration = registrationRepository.save(registration);

        // Registration alone is valid and must not create an empty invoice/order.
        boolean hasTests = request.getTestMasterIds() != null && !request.getTestMasterIds().isEmpty();
        boolean hasPackages = request.getPackageIds() != null && !request.getPackageIds().isEmpty();
        if (!hasTests && !hasPackages) {
            return registrationMapper.toResponseDto(savedRegistration);
        }

        LabOrder order = new LabOrder();
        order.setRegistration(savedRegistration);
        order.setReferringDoctorName("Self / No referring doctor");
        BigDecimal gross = BigDecimal.ZERO;
        for (Long testId : request.getTestMasterIds() == null ? java.util.List.<Long>of() : request.getTestMasterIds()) {
            TestMaster master = testMasterRepository.findById(testId)
                    .orElseThrow(() -> new IllegalArgumentException("Inactive or unknown test: " + testId));
            BigDecimal price = master.getDefaultPrice();
            LabTestRequest item = new LabTestRequest();
            item.setLabOrder(order);
            item.setTestMaster(master);
            item.setBasePrice(price);
            item.setDiscountAmount(BigDecimal.ZERO);
            item.setPriceCharged(price);
            item.setPriority(TestPriority.Routine);
            item.setStatus(TestStatus.PENDING_COLLECTION);
            order.getTestRequests().add(item);
            gross = gross.add(price);
        }
        for (Long packageId : request.getPackageIds() == null ? java.util.List.<Long>of() : request.getPackageIds()) {
            TestPackage testPackage = testPackageRepository.findById(packageId)
                    .filter(TestPackage::isActive)
                    .orElseThrow(() -> new IllegalArgumentException("Inactive or unknown package: " + packageId));
            LabOrderPackage orderPackage = new LabOrderPackage();
            orderPackage.setLabOrder(order);
            orderPackage.setTestPackage(testPackage);
            orderPackage.setPackageCode(testPackage.getCode());
            orderPackage.setPackageName(testPackage.getName());
            orderPackage.setPackagePrice(testPackage.getPackagePrice());
            order.getPackages().add(orderPackage);
            gross = gross.add(testPackage.getPackagePrice());
            for (TestPackageItem packageItem : testPackage.getItems()) {
                LabTestRequest item = new LabTestRequest();
                item.setLabOrder(order);
                item.setTestMaster(packageItem.getTestMaster());
                item.setTestPackage(testPackage);
                item.setBasePrice(packageItem.getPackageTestPrice());
                item.setDiscountAmount(BigDecimal.ZERO);
                item.setPriceCharged(packageItem.getPackageTestPrice());
                item.setPriority(TestPriority.Routine);
                item.setStatus(TestStatus.PENDING_COLLECTION);
                order.getTestRequests().add(item);
            }
        }
        BigDecimal discount = request.getDiscountAmount() == null ? BigDecimal.ZERO : request.getDiscountAmount();
        if (discount.compareTo(gross) > 0 || discount.signum() < 0) throw new IllegalArgumentException("Invalid discount");
        order.setGrossAmount(gross);
        order.setTotalDiscount(discount);
        order.setNetAmount(gross.subtract(discount));
        labOrderRepository.save(order);

        BillingInvoice invoice = new BillingInvoice();
        invoice.setRegistration(savedRegistration);
        invoice.setInvoiceNumber("INV-" + LocalDate.now().getYear() + "-" + String.format("%04d", new Random().nextInt(9999) + 1));
        invoice.setDiscount(BigDecimal.ZERO);
        invoice.setTaxRate(BigDecimal.ZERO);
        invoice.setAmountPaid(BigDecimal.ZERO);
        invoice.setCreatedAt(LocalDate.now());
        invoice.setDueDate(LocalDate.now());

        BillingInvoiceItem registrationItem = new BillingInvoiceItem();
        registrationItem.setInvoice(invoice);
        registrationItem.setCode("REG-FEE");
        registrationItem.setName("Registration fee");
        registrationItem.setPrice(savedRegistration.getRegistrationAmount());
        registrationItem.setQuantity(1);
        invoice.getItems().add(registrationItem);
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

        return registrationMapper.toResponseDto(savedRegistration);
    }

    @Override
    public RegistrationResponseDto getRegistrationById(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        return registrationMapper.toResponseDto(registration);
    }

    @Override
    public List<RegistrationResponseDto> getAllRegistrations() {

        List<Registration> registrations = registrationRepository.findAll();

        return registrations.stream()
                .map(registrationMapper::toResponseDto)
                .toList();
    }

    @Override
    public Page<RegistrationResponseDto> searchRegistrations(String search, LocalDateTime fromDate, LocalDateTime toDate, String packageName, org.springframework.data.domain.Pageable pageable) {
        return registrationRepository.searchRegistrations(search == null ? "" : search.trim(), fromDate, toDate, packageName == null ? "" : packageName.trim(), pageable)
                .map(registrationMapper::toResponseDto);
    }

    @Transactional
    @Override
    public RegistrationResponseDto updateRegistration(Long id, RegistrationRequestDto request) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setAge(request.getAge());
        registration.setGender(request.getGender());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setRegistrationAmount(request.getRegistrationAmount() == null ? BigDecimal.ZERO : request.getRegistrationAmount());
        registration.setUpdatedAt(LocalDateTime.now());

        registrationDispatchMethodRepository.deleteAllByRegistration(registration);
        registrationDispatchMethodRepository.flush();

        registration.getDispatchMethods().clear();

        List<RegistrationDispatchMethod> newDispatchMethods =
                registrationMapper.toDispatchMethodEntities(
                        request.getDispatchMethods(),
                        registration
                );

        registration.getDispatchMethods().addAll(newDispatchMethods);

        return registrationMapper.toResponseDto(registration);
    }

    @Override
    public void deleteRegistration(Long id) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        registrationRepository.delete(registration);
    }
}

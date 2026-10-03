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

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final RegistrationDispatchMethodRepository registrationDispatchMethodRepository;
    private final BillingInvoiceRepository billingInvoiceRepository;

    public RegistrationServiceImpl(
            RegistrationRepository registrationRepository,
            RegistrationMapper registrationMapper,
            RegistrationDispatchMethodRepository registrationDispatchMethodRepository,
            BillingInvoiceRepository billingInvoiceRepository) {

        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.registrationDispatchMethodRepository = registrationDispatchMethodRepository;
        this.billingInvoiceRepository = billingInvoiceRepository;
    }

    @Override
    @Transactional
    public RegistrationResponseDto createRegistration(RegistrationRequestDto request) {

        Registration registration = registrationMapper.toEntity(request);

        registration.setRegistrationDate(LocalDateTime.now());
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

        // Every registration creates the patient's initial bill, even when no
        // laboratory tests are ordered. Tests can be added to later invoices.
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

    @Transactional
    @Override
    public RegistrationResponseDto updateRegistration(Long id, RegistrationRequestDto request) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with ID: " + id));

        registration.setFirstName(request.getFirstName());
        registration.setLastName(request.getLastName());
        registration.setDateOfBirth(request.getDateOfBirth());
        registration.setGender(request.getGender());
        registration.setPhone(request.getPhone());
        registration.setEmail(request.getEmail());
        registration.setReferringDoctorId(request.getReferringDoctorId());
        registration.setRateListId(request.getRateListId());
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

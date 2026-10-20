package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageItemRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageItemResponseDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageRequestDto;
import com.apuxlabs.apuxlabs_api.testorder.dto.TestPackageResponseDto;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestPackage;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestPackageItem;
import com.apuxlabs.apuxlabs_api.testorder.entity.TestMaster;
import com.apuxlabs.apuxlabs_api.testorder.repository.TestMasterRepository;
import com.apuxlabs.apuxlabs_api.testorder.repository.TestPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestPackageService {
    private final TestPackageRepository testPackageRepository;
    private final TestMasterRepository testMasterRepository;

    @Transactional(readOnly = true)
    public List<TestPackageResponseDto> getActivePackages() {
        return testPackageRepository.findByActiveTrueOrderByNameAsc().stream().map(this::toDto).toList();
    }

    @Transactional
    public TestPackageResponseDto createPackage(TestPackageRequestDto request) {
        TestPackage testPackage = new TestPackage();
        apply(testPackage, request);
        return toDto(testPackageRepository.save(testPackage));
    }

    @Transactional
    public TestPackageResponseDto updatePackage(Long id, TestPackageRequestDto request) {
        TestPackage testPackage = testPackageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + id));
        apply(testPackage, request);
        return toDto(testPackage);
    }

    private void apply(TestPackage testPackage, TestPackageRequestDto request) {
        if (request.getCode() == null || request.getCode().isBlank()) throw new IllegalArgumentException("Package code is required");
        if (request.getName() == null || request.getName().isBlank()) throw new IllegalArgumentException("Package name is required");
        if (request.getPackagePrice() == null || request.getPackagePrice().signum() < 0) throw new IllegalArgumentException("Invalid package price");
        if (request.getItems() == null || request.getItems().isEmpty()) throw new IllegalArgumentException("A package must contain at least one test");

        testPackage.setCode(request.getCode().trim());
        testPackage.setName(request.getName().trim());
        testPackage.setPackagePrice(request.getPackagePrice());
        testPackage.setActive(true);
        if (testPackage.getCreatedAt() == null) testPackage.setCreatedAt(LocalDateTime.now());
        testPackage.setUpdatedAt(LocalDateTime.now());
        testPackage.getItems().clear();

        for (TestPackageItemRequestDto itemRequest : request.getItems()) {
            TestMaster master = testMasterRepository.findById(itemRequest.getTestMasterId())
                    .orElseThrow(() -> new IllegalArgumentException("Test not found: " + itemRequest.getTestMasterId()));
            BigDecimal price = itemRequest.getPackageTestPrice() == null ? master.getDefaultPrice() : itemRequest.getPackageTestPrice();
            if (price.signum() < 0) throw new IllegalArgumentException("Invalid package test price");
            TestPackageItem item = new TestPackageItem();
            item.setTestPackage(testPackage);
            item.setTestMaster(master);
            item.setPackageTestPrice(price);
            testPackage.getItems().add(item);
        }
    }

    private TestPackageResponseDto toDto(TestPackage testPackage) {
        List<TestPackageItemResponseDto> items = testPackage.getItems().stream()
                .map(item -> new TestPackageItemResponseDto(item.getId(), item.getTestMaster().getId(), item.getTestMaster().getCode(), item.getTestMaster().getName(), item.getPackageTestPrice()))
                .toList();
        return new TestPackageResponseDto(testPackage.getId(), testPackage.getCode(), testPackage.getName(), testPackage.getPackagePrice(), testPackage.isActive(), items);
    }
}

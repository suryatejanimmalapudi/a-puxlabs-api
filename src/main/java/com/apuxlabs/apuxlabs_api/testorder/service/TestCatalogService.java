package com.apuxlabs.apuxlabs_api.testorder.service;

import com.apuxlabs.apuxlabs_api.testorder.dto.TestMasterDto;
import com.apuxlabs.apuxlabs_api.testorder.repository.TestMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCatalogService {

    private final TestMasterRepository testMasterRepository;

    @Transactional(readOnly = true)
    public List<TestMasterDto> getAllTests() {
        return testMasterRepository.findAll().stream().map(test -> {
            TestMasterDto dto = new TestMasterDto();
            dto.setId(test.getId());
            dto.setCode(test.getCode());
            dto.setName(test.getName());
            dto.setDepartment(test.getDepartment());
            dto.setDefaultPrice(test.getDefaultPrice());
            return dto;
        }).collect(Collectors.toList());
    }
}
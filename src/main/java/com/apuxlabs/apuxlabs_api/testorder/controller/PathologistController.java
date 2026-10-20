package com.apuxlabs.apuxlabs_api.testorder.controller;

import com.apuxlabs.apuxlabs_api.testorder.dto.PathologistDto;
import com.apuxlabs.apuxlabs_api.testorder.repository.PathologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pathologists")
@RequiredArgsConstructor
public class PathologistController {
    private final PathologistRepository pathologistRepository;

    @GetMapping
    public List<PathologistDto> getActivePathologists() {
        return pathologistRepository.findByActiveTrueOrderByNameAsc().stream()
                .map(PathologistDto::from)
                .toList();
    }
}

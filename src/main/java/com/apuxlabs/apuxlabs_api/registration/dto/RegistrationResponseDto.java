package com.apuxlabs.apuxlabs_api.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponseDto {

    private Long id;

    private String designation;

    private String firstName;

    private String lastName;

    private Integer age;

    private String gender;

    private String phone;

    private String email;

    private BigDecimal registrationAmount;

    private LocalDateTime registrationDate;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> dispatchMethods;
}

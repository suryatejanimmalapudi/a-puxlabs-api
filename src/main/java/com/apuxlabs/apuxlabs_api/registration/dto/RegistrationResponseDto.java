package com.apuxlabs.apuxlabs_api.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponseDto {

    private Long id;

    private String designation;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phone;

    private String email;

    private Long referringDoctorId;

    private Long rateListId;

    private LocalDateTime registrationDate;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> dispatchMethods;
}
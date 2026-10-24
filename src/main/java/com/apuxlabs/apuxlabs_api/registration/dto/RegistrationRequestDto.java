package com.apuxlabs.apuxlabs_api.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequestDto {

    private String uhid;

    private String firstName;

    private String lastName;

    private Integer age;

    private String gender;

    private String phone;

    private String email;

    private BigDecimal registrationAmount;

    /** Catalog IDs only; prices and names are resolved by the backend. */
    private List<Long> testMasterIds = new ArrayList<>();
    private List<Long> packageIds = new ArrayList<>();
    private BigDecimal discountAmount = BigDecimal.ZERO;

    private List<String> dispatchMethods;
}

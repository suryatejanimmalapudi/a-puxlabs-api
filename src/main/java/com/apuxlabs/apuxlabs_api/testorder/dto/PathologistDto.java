package com.apuxlabs.apuxlabs_api.testorder.dto;

import com.apuxlabs.apuxlabs_api.testorder.entity.Pathologist;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PathologistDto {
    private Long id;
    private String name;
    private String designation;
    private String registrationNumber;
    private String signatureImagePath;
    private boolean defaultPathologist;

    public static PathologistDto from(Pathologist pathologist) {
        return new PathologistDto(pathologist.getId(), pathologist.getName(), pathologist.getDesignation(),
                pathologist.getRegistrationNumber(), pathologist.getSignatureImagePath(), pathologist.isDefaultPathologist());
    }
}

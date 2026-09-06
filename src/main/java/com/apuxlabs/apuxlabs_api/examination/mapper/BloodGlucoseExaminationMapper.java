package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.BloodGlucoseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.BloodGlucoseExamination;
import org.springframework.stereotype.Component;

@Component
public class BloodGlucoseExaminationMapper {

    /**
     * Converts request data into a new BloodGlucoseExamination entity.
     * Registration and audit fields are assigned in the service layer.
     */
    public BloodGlucoseExamination toEntity(
            BloodGlucoseExaminationRequestDto requestDto) {

        BloodGlucoseExamination examination =
                new BloodGlucoseExamination();

        examination.setExaminationDateTime(
                requestDto.getExaminationDateTime()
        );

        examination.setTestType(
                requestDto.getTestType()
        );

        examination.setGlucoseValue(
                requestDto.getGlucoseValue()
        );

        examination.setSampleType(
                requestDto.getSampleType()
        );

        examination.setReferenceRange(
                requestDto.getReferenceRange()
        );

        examination.setUnit(
                requestDto.getUnit()
        );

        examination.setRemarks(
                requestDto.getRemarks()
        );

        examination.setPathologistName(
                requestDto.getPathologistName()
        );

        return examination;
    }

    /**
     * Converts a BloodGlucoseExamination entity into the API response DTO.
     */
    public BloodGlucoseExaminationResponseDto toResponseDto(
            BloodGlucoseExamination examination) {

        BloodGlucoseExaminationResponseDto responseDto =
                new BloodGlucoseExaminationResponseDto();

        responseDto.setId(
                examination.getId()
        );

        responseDto.setRegistrationId(
                examination.getRegistration() != null
                        ? examination.getRegistration().getId()
                        : null
        );

        responseDto.setExaminationDateTime(
                examination.getExaminationDateTime()
        );

        responseDto.setTestType(
                examination.getTestType()
        );

        responseDto.setGlucoseValue(
                examination.getGlucoseValue()
        );

        responseDto.setSampleType(
                examination.getSampleType()
        );

        responseDto.setReferenceRange(
                examination.getReferenceRange()
        );

        responseDto.setUnit(
                examination.getUnit()
        );

        responseDto.setRemarks(
                examination.getRemarks()
        );

        responseDto.setPathologistName(
                examination.getPathologistName()
        );

        responseDto.setCreatedAt(
                examination.getCreatedAt()
        );

        responseDto.setUpdatedAt(
                examination.getUpdatedAt()
        );

        return responseDto;
    }
}
package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.RenalFunctionExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.RenalFunctionExamination;
import org.springframework.stereotype.Component;

@Component
public class RenalFunctionExaminationMapper {

    /**
     * Converts renal function examination request data
     * into a new entity.
     *
     * Registration and audit fields are assigned
     * in the service layer.
     *
     * @param request renal function examination details
     * @return renal function examination entity
     */
    public RenalFunctionExamination toEntity(
            RenalFunctionExaminationRequestDto request) {

        RenalFunctionExamination examination =
                new RenalFunctionExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setTestType(
                request.getTestType()
        );

        examination.setResultValue(
                request.getResultValue()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setReferenceRange(
                request.getReferenceRange()
        );

        examination.setUnit(
                request.getUnit()
        );

        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        return examination;
    }

    /**
     * Converts a renal function examination entity
     * into its API response DTO.
     *
     * @param examination renal function examination entity
     * @return renal function examination response
     */
    public RenalFunctionExaminationResponseDto toResponseDto(
            RenalFunctionExamination examination) {

        RenalFunctionExaminationResponseDto response =
                new RenalFunctionExaminationResponseDto();

        response.setId(
                examination.getId()
        );

        response.setRegistrationId(
                examination.getRegistration() != null
                        ? examination.getRegistration().getId()
                        : null
        );

        response.setExaminationDateTime(
                examination.getExaminationDateTime()
        );

        response.setTestType(
                examination.getTestType()
        );

        response.setResultValue(
                examination.getResultValue()
        );

        response.setSampleType(
                examination.getSampleType()
        );

        response.setReferenceRange(
                examination.getReferenceRange()
        );

        response.setUnit(
                examination.getUnit()
        );

        response.setRemarks(
                examination.getRemarks()
        );

        response.setPathologistName(
                examination.getPathologistName()
        );

        response.setCreatedAt(
                examination.getCreatedAt()
        );

        response.setUpdatedAt(
                examination.getUpdatedAt()
        );

        return response;
    }
}
package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.SerumCholinesteraseExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.SerumCholinesteraseExamination;
import org.springframework.stereotype.Component;

@Component
public class SerumCholinesteraseExaminationMapper {

    /**
     * Converts serum cholinesterase examination request data
     * into a new entity.
     *
     * Registration and audit fields are assigned
     * in the service layer.
     *
     * @param request serum cholinesterase examination details
     * @return serum cholinesterase examination entity
     */
    public SerumCholinesteraseExamination toEntity(
            SerumCholinesteraseExaminationRequestDto request) {

        SerumCholinesteraseExamination examination =
                new SerumCholinesteraseExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setResultValue(
                request.getResultValue()
        );

        examination.setMethod(
                request.getMethod()
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
     * Converts a serum cholinesterase examination entity
     * into its API response DTO.
     *
     * @param examination serum cholinesterase examination entity
     * @return serum cholinesterase examination response
     */
    public SerumCholinesteraseExaminationResponseDto toResponseDto(
            SerumCholinesteraseExamination examination) {

        SerumCholinesteraseExaminationResponseDto response =
                new SerumCholinesteraseExaminationResponseDto();

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

        response.setResultValue(
                examination.getResultValue()
        );

        response.setMethod(
                examination.getMethod()
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
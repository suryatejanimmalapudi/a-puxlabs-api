package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.EyeExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.EyeExamination;
import org.springframework.stereotype.Component;

@Component
public class EyeExaminationMapper {

    /**
     * Converts an incoming eye examination request DTO into an entity.
     *
     * Registration, timestamps, and database-generated values are handled
     * separately by the service/database layer.
     *
     * @param dto incoming eye examination request
     * @return EyeExamination entity
     */
    public EyeExamination toEntity(EyeExaminationRequestDto dto) {

        EyeExamination examination = new EyeExamination();

        examination.setExaminationDate(dto.getExaminationDate());

        examination.setRightDistanceSph(dto.getRightDistanceSph());
        examination.setRightDistanceCyl(dto.getRightDistanceCyl());
        examination.setRightDistanceAxis(dto.getRightDistanceAxis());
        examination.setRightDistanceVa(dto.getRightDistanceVa());

        examination.setLeftDistanceSph(dto.getLeftDistanceSph());
        examination.setLeftDistanceCyl(dto.getLeftDistanceCyl());
        examination.setLeftDistanceAxis(dto.getLeftDistanceAxis());
        examination.setLeftDistanceVa(dto.getLeftDistanceVa());

        examination.setRightNearSph(dto.getRightNearSph());
        examination.setRightNearCyl(dto.getRightNearCyl());
        examination.setRightNearAxis(dto.getRightNearAxis());
        examination.setRightNearVa(dto.getRightNearVa());

        examination.setLeftNearSph(dto.getLeftNearSph());
        examination.setLeftNearCyl(dto.getLeftNearCyl());
        examination.setLeftNearAxis(dto.getLeftNearAxis());
        examination.setLeftNearVa(dto.getLeftNearVa());

        examination.setColourVision(dto.getColourVision());
        examination.setRemarks(dto.getRemarks());
        examination.setOptometristName(dto.getOptometristName());

        return examination;
    }

    /**
     * Converts a persisted EyeExamination entity into an API response DTO.
     *
     * @param examination persisted eye examination entity
     * @return eye examination response DTO
     */
    public EyeExaminationResponseDto toResponseDto(
            EyeExamination examination) {

        EyeExaminationResponseDto response =
                new EyeExaminationResponseDto();

        response.setId(examination.getId());

        if (examination.getRegistration() != null) {
            response.setRegistrationId(
                    examination.getRegistration().getId()
            );
        }

        response.setExaminationDate(examination.getExaminationDate());

        response.setRightDistanceSph(examination.getRightDistanceSph());
        response.setRightDistanceCyl(examination.getRightDistanceCyl());
        response.setRightDistanceAxis(examination.getRightDistanceAxis());
        response.setRightDistanceVa(examination.getRightDistanceVa());

        response.setLeftDistanceSph(examination.getLeftDistanceSph());
        response.setLeftDistanceCyl(examination.getLeftDistanceCyl());
        response.setLeftDistanceAxis(examination.getLeftDistanceAxis());
        response.setLeftDistanceVa(examination.getLeftDistanceVa());

        response.setRightNearSph(examination.getRightNearSph());
        response.setRightNearCyl(examination.getRightNearCyl());
        response.setRightNearAxis(examination.getRightNearAxis());
        response.setRightNearVa(examination.getRightNearVa());

        response.setLeftNearSph(examination.getLeftNearSph());
        response.setLeftNearCyl(examination.getLeftNearCyl());
        response.setLeftNearAxis(examination.getLeftNearAxis());
        response.setLeftNearVa(examination.getLeftNearVa());

        response.setColourVision(examination.getColourVision());
        response.setRemarks(examination.getRemarks());
        response.setOptometristName(examination.getOptometristName());

        response.setCreatedAt(examination.getCreatedAt());
        response.setUpdatedAt(examination.getUpdatedAt());

        return response;
    }
}
package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LipidProfileExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LipidProfileExamination;
import org.springframework.stereotype.Component;

@Component
public class LipidProfileExaminationMapper {

    public LipidProfileExamination toEntity(
            LipidProfileExaminationRequestDto request) {

        LipidProfileExamination examination =
                new LipidProfileExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setTotalCholesterol(
                request.getTotalCholesterol()
        );

        examination.setTriglycerides(
                request.getTriglycerides()
        );

        examination.setHdlCholesterol(
                request.getHdlCholesterol()
        );

        examination.setLdlCholesterol(
                request.getLdlCholesterol()
        );

        examination.setVldlCholesterol(
                request.getVldlCholesterol()
        );

        examination.setLdlHdlRatio(
                request.getLdlHdlRatio()
        );

        examination.setTotalCholesterolHdlRatio(
                request.getTotalCholesterolHdlRatio()
        );

        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        return examination;
    }

    public LipidProfileExaminationResponseDto toResponseDto(
            LipidProfileExamination examination) {

        LipidProfileExaminationResponseDto response =
                new LipidProfileExaminationResponseDto();

        response.setId(examination.getId());

        response.setRegistrationId(
                examination.getRegistration().getId()
        );

        response.setExaminationDateTime(
                examination.getExaminationDateTime()
        );

        response.setSampleType(
                examination.getSampleType()
        );

        response.setTotalCholesterol(
                examination.getTotalCholesterol()
        );

        response.setTriglycerides(
                examination.getTriglycerides()
        );

        response.setHdlCholesterol(
                examination.getHdlCholesterol()
        );

        response.setLdlCholesterol(
                examination.getLdlCholesterol()
        );

        response.setVldlCholesterol(
                examination.getVldlCholesterol()
        );

        response.setLdlHdlRatio(
                examination.getLdlHdlRatio()
        );

        response.setTotalCholesterolHdlRatio(
                examination.getTotalCholesterolHdlRatio()
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
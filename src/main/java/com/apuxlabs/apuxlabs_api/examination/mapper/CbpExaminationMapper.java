package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.CbpExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.CbpExamination;
import org.springframework.stereotype.Component;

@Component
public class CbpExaminationMapper {

    /**
     * Converts a CBP examination request DTO into an entity.
     *
     * Application-managed fields such as ID, registration,
     * createdAt, and updatedAt are not set by the mapper.
     *
     * @param request CBP examination details received from the client
     * @return CBP examination entity
     */
    public CbpExamination toEntity(
            CbpExaminationRequestDto request) {

        CbpExamination examination =
                new CbpExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        // Basic haematology
        examination.setHaemoglobin(
                request.getHaemoglobin()
        );

        examination.setTotalLeukocyteCount(
                request.getTotalLeukocyteCount()
        );

        // Differential leukocyte count
        examination.setNeutrophils(
                request.getNeutrophils()
        );

        examination.setLymphocytes(
                request.getLymphocytes()
        );

        examination.setEosinophils(
                request.getEosinophils()
        );

        examination.setMonocytes(
                request.getMonocytes()
        );

        examination.setBasophils(
                request.getBasophils()
        );

        // Absolute leukocyte count
        examination.setAbsoluteNeutrophils(
                request.getAbsoluteNeutrophils()
        );

        examination.setAbsoluteLymphocytes(
                request.getAbsoluteLymphocytes()
        );

        examination.setAbsoluteEosinophils(
                request.getAbsoluteEosinophils()
        );

        examination.setAbsoluteMonocytes(
                request.getAbsoluteMonocytes()
        );

        // RBC indices
        examination.setRbcCount(
                request.getRbcCount()
        );

        examination.setHct(
                request.getHct()
        );

        examination.setMcv(
                request.getMcv()
        );

        examination.setMch(
                request.getMch()
        );

        examination.setMchc(
                request.getMchc()
        );

        examination.setRdwCv(
                request.getRdwCv()
        );

        examination.setRdwSd(
                request.getRdwSd()
        );

        // Platelet indices
        examination.setPlateletCount(
                request.getPlateletCount()
        );

        examination.setPct(
                request.getPct()
        );

        examination.setMpv(
                request.getMpv()
        );

        examination.setPdw(
                request.getPdw()
        );

        examination.setPLcr(
                request.getPLcr()
        );

        examination.setPLcc(
                request.getPLcc()
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
     * Converts a CBP examination entity into a response DTO.
     *
     * The response includes both examination results and
     * application-managed fields such as ID, registration ID,
     * createdAt, and updatedAt.
     *
     * @param examination CBP examination entity
     * @return CBP examination response DTO
     */
    public CbpExaminationResponseDto toResponseDto(
            CbpExamination examination) {

        CbpExaminationResponseDto response =
                new CbpExaminationResponseDto();

        response.setId(
                examination.getId()
        );

        if (examination.getRegistration() != null) {
            response.setRegistrationId(
                    examination.getRegistration().getId()
            );
        }

        response.setExaminationDateTime(
                examination.getExaminationDateTime()
        );

        // Basic haematology
        response.setHaemoglobin(
                examination.getHaemoglobin()
        );

        response.setTotalLeukocyteCount(
                examination.getTotalLeukocyteCount()
        );

        // Differential leukocyte count
        response.setNeutrophils(
                examination.getNeutrophils()
        );

        response.setLymphocytes(
                examination.getLymphocytes()
        );

        response.setEosinophils(
                examination.getEosinophils()
        );

        response.setMonocytes(
                examination.getMonocytes()
        );

        response.setBasophils(
                examination.getBasophils()
        );

        // Absolute leukocyte count
        response.setAbsoluteNeutrophils(
                examination.getAbsoluteNeutrophils()
        );

        response.setAbsoluteLymphocytes(
                examination.getAbsoluteLymphocytes()
        );

        response.setAbsoluteEosinophils(
                examination.getAbsoluteEosinophils()
        );

        response.setAbsoluteMonocytes(
                examination.getAbsoluteMonocytes()
        );

        // RBC indices
        response.setRbcCount(
                examination.getRbcCount()
        );

        response.setHct(
                examination.getHct()
        );

        response.setMcv(
                examination.getMcv()
        );

        response.setMch(
                examination.getMch()
        );

        response.setMchc(
                examination.getMchc()
        );

        response.setRdwCv(
                examination.getRdwCv()
        );

        response.setRdwSd(
                examination.getRdwSd()
        );

        // Platelet indices
        response.setPlateletCount(
                examination.getPlateletCount()
        );

        response.setPct(
                examination.getPct()
        );

        response.setMpv(
                examination.getMpv()
        );

        response.setPdw(
                examination.getPdw()
        );

        response.setPLcr(
                examination.getPLcr()
        );

        response.setPLcc(
                examination.getPLcc()
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
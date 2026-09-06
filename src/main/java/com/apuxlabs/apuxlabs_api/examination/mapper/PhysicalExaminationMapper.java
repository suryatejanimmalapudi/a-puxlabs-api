package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.PhysicalExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.PhysicalExamination;
import org.springframework.stereotype.Component;

@Component
public class PhysicalExaminationMapper {

    /**
     * Converts the incoming request DTO into a PhysicalExamination entity.
     *
     * The registration relationship is not mapped here because the
     * registration is resolved and assigned by the service layer.
     *
     * @param dto incoming physical examination request
     * @return PhysicalExamination entity
     */
    public PhysicalExamination toEntity(
            PhysicalExaminationRequestDto dto) {

        PhysicalExamination examination = new PhysicalExamination();

        examination.setExaminationDate(dto.getExaminationDate());
        examination.setHeight(dto.getHeight());
        examination.setWeight(dto.getWeight());
        examination.setPulseRate(dto.getPulseRate());
        examination.setBloodPressure(dto.getBloodPressure());
        examination.setTemperature(dto.getTemperature());
        examination.setPresentComplaints(dto.getPresentComplaints());
        examination.setPastHistory(dto.getPastHistory());
        examination.setCardiovascularSystem(dto.getCardiovascularSystem());
        examination.setRespiratorySystem(dto.getRespiratorySystem());
        examination.setAbdomen(dto.getAbdomen());
        examination.setCentralNervousSystem(dto.getCentralNervousSystem());
        examination.setSkinExamination(dto.getSkinExamination());
        examination.setEpilepsy(dto.getEpilepsy());
        examination.setMedicalCertificate(dto.getMedicalCertificate());
        examination.setDoctorName(dto.getDoctorName());
        examination.setDoctorRegistrationNumber(
                dto.getDoctorRegistrationNumber()
        );

        return examination;
    }

    /**
     * Converts a PhysicalExamination entity into a response DTO.
     *
     * @param examination persisted physical examination
     * @return physical examination response DTO
     */
    public PhysicalExaminationResponseDto toResponseDto(
            PhysicalExamination examination) {

        PhysicalExaminationResponseDto response =
                new PhysicalExaminationResponseDto();

        response.setId(examination.getId());

        if (examination.getRegistration() != null) {
            response.setRegistrationId(
                    examination.getRegistration().getId()
            );
        }

        response.setExaminationDate(examination.getExaminationDate());
        response.setHeight(examination.getHeight());
        response.setWeight(examination.getWeight());
        response.setPulseRate(examination.getPulseRate());
        response.setBloodPressure(examination.getBloodPressure());
        response.setTemperature(examination.getTemperature());
        response.setPresentComplaints(
                examination.getPresentComplaints()
        );
        response.setPastHistory(examination.getPastHistory());
        response.setCardiovascularSystem(
                examination.getCardiovascularSystem()
        );
        response.setRespiratorySystem(
                examination.getRespiratorySystem()
        );
        response.setAbdomen(examination.getAbdomen());
        response.setCentralNervousSystem(
                examination.getCentralNervousSystem()
        );
        response.setSkinExamination(
                examination.getSkinExamination()
        );
        response.setEpilepsy(examination.getEpilepsy());
        response.setMedicalCertificate(
                examination.getMedicalCertificate()
        );
        response.setDoctorName(examination.getDoctorName());
        response.setDoctorRegistrationNumber(
                examination.getDoctorRegistrationNumber()
        );
        response.setCreatedAt(examination.getCreatedAt());
        response.setUpdatedAt(examination.getUpdatedAt());

        return response;
    }
}
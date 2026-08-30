package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.UrineRoutineExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.UrineRoutineExamination;
import org.springframework.stereotype.Component;

@Component
public class UrineRoutineExaminationMapper {

    public UrineRoutineExamination toEntity(
            UrineRoutineExaminationRequestDto request) {

        UrineRoutineExamination examination =
                new UrineRoutineExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        // Physical examination
        examination.setColour(
                request.getColour()
        );

        examination.setDeposit(
                request.getDeposit()
        );

        examination.setAppearance(
                request.getAppearance()
        );

        examination.setPh(
                request.getPh()
        );

        examination.setSpecificGravity(
                request.getSpecificGravity()
        );

        examination.setQuantity(
                request.getQuantity()
        );

        // Chemical examination
        examination.setUrineProtein(
                request.getUrineProtein()
        );

        examination.setBileSalt(
                request.getBileSalt()
        );

        examination.setUrineGlucose(
                request.getUrineGlucose()
        );

        examination.setUrineKetones(
                request.getUrineKetones()
        );

        examination.setBilePigment(
                request.getBilePigment()
        );

        examination.setOccultBlood(
                request.getOccultBlood()
        );

        // Microscopic examination
        examination.setRbcs(
                request.getRbcs()
        );

        examination.setPusCells(
                request.getPusCells()
        );

        examination.setEpithelialCells(
                request.getEpithelialCells()
        );

        examination.setCrystals(
                request.getCrystals()
        );

        examination.setCasts(
                request.getCasts()
        );

        examination.setAmorphousDeposit(
                request.getAmorphousDeposit()
        );

        examination.setBacteria(
                request.getBacteria()
        );

        examination.setTrichomonasVaginalis(
                request.getTrichomonasVaginalis()
        );

        examination.setYeastCells(
                request.getYeastCells()
        );

        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        return examination;
    }

    public UrineRoutineExaminationResponseDto toResponseDto(
            UrineRoutineExamination examination) {

        UrineRoutineExaminationResponseDto response =
                new UrineRoutineExaminationResponseDto();

        response.setId(
                examination.getId()
        );

        response.setRegistrationId(
                examination.getRegistration().getId()
        );

        response.setExaminationDateTime(
                examination.getExaminationDateTime()
        );

        response.setSampleType(
                examination.getSampleType()
        );

        // Physical examination
        response.setColour(
                examination.getColour()
        );

        response.setDeposit(
                examination.getDeposit()
        );

        response.setAppearance(
                examination.getAppearance()
        );

        response.setPh(
                examination.getPh()
        );

        response.setSpecificGravity(
                examination.getSpecificGravity()
        );

        response.setQuantity(
                examination.getQuantity()
        );

        // Chemical examination
        response.setUrineProtein(
                examination.getUrineProtein()
        );

        response.setBileSalt(
                examination.getBileSalt()
        );

        response.setUrineGlucose(
                examination.getUrineGlucose()
        );

        response.setUrineKetones(
                examination.getUrineKetones()
        );

        response.setBilePigment(
                examination.getBilePigment()
        );

        response.setOccultBlood(
                examination.getOccultBlood()
        );

        // Microscopic examination
        response.setRbcs(
                examination.getRbcs()
        );

        response.setPusCells(
                examination.getPusCells()
        );

        response.setEpithelialCells(
                examination.getEpithelialCells()
        );

        response.setCrystals(
                examination.getCrystals()
        );

        response.setCasts(
                examination.getCasts()
        );

        response.setAmorphousDeposit(
                examination.getAmorphousDeposit()
        );

        response.setBacteria(
                examination.getBacteria()
        );

        response.setTrichomonasVaginalis(
                examination.getTrichomonasVaginalis()
        );

        response.setYeastCells(
                examination.getYeastCells()
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
package com.apuxlabs.apuxlabs_api.examination.mapper;

import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationRequestDto;
import com.apuxlabs.apuxlabs_api.examination.dto.LiverFunctionTestExaminationResponseDto;
import com.apuxlabs.apuxlabs_api.examination.entity.LiverFunctionTestExamination;
import org.springframework.stereotype.Component;

@Component
public class LiverFunctionTestExaminationMapper {

    public LiverFunctionTestExamination toEntity(
            LiverFunctionTestExaminationRequestDto request) {

        LiverFunctionTestExamination examination =
                new LiverFunctionTestExamination();

        examination.setExaminationDateTime(
                request.getExaminationDateTime()
        );

        examination.setSampleType(
                request.getSampleType()
        );

        examination.setBilirubinTotal(
                request.getBilirubinTotal()
        );

        examination.setBilirubinDirect(
                request.getBilirubinDirect()
        );

        examination.setBilirubinIndirect(
                request.getBilirubinIndirect()
        );

        examination.setSgpt(
                request.getSgpt()
        );

        examination.setSgot(
                request.getSgot()
        );

        examination.setSgotSgptRatio(
                request.getSgotSgptRatio()
        );

        examination.setAlkalinePhosphatase(
                request.getAlkalinePhosphatase()
        );

        examination.setGammaGlutamylTransferase(
                request.getGammaGlutamylTransferase()
        );

        examination.setTotalProteins(
                request.getTotalProteins()
        );

        examination.setAlbumin(
                request.getAlbumin()
        );

        examination.setGlobulin(
                request.getGlobulin()
        );

        examination.setAlbuminGlobulinRatio(
                request.getAlbuminGlobulinRatio()
        );

        examination.setRemarks(
                request.getRemarks()
        );

        examination.setPathologistName(
                request.getPathologistName()
        );

        return examination;
    }

    public LiverFunctionTestExaminationResponseDto toResponseDto(
            LiverFunctionTestExamination examination) {

        LiverFunctionTestExaminationResponseDto response =
                new LiverFunctionTestExaminationResponseDto();

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

        response.setBilirubinTotal(
                examination.getBilirubinTotal()
        );

        response.setBilirubinDirect(
                examination.getBilirubinDirect()
        );

        response.setBilirubinIndirect(
                examination.getBilirubinIndirect()
        );

        response.setSgpt(
                examination.getSgpt()
        );

        response.setSgot(
                examination.getSgot()
        );

        response.setSgotSgptRatio(
                examination.getSgotSgptRatio()
        );

        response.setAlkalinePhosphatase(
                examination.getAlkalinePhosphatase()
        );

        response.setGammaGlutamylTransferase(
                examination.getGammaGlutamylTransferase()
        );

        response.setTotalProteins(
                examination.getTotalProteins()
        );

        response.setAlbumin(
                examination.getAlbumin()
        );

        response.setGlobulin(
                examination.getGlobulin()
        );

        response.setAlbuminGlobulinRatio(
                examination.getAlbuminGlobulinRatio()
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
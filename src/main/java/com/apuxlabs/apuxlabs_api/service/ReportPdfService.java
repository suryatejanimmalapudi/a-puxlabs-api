package com.apuxlabs.apuxlabs_api.service;

import com.apuxlabs.apuxlabs_api.testorder.entity.LabTestRequest;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.Cell;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.util.List;
import java.util.UUID;

@Service
public class ReportPdfService {

    private final ReportQrCodeService reportQrCodeService;

    public ReportPdfService(ReportQrCodeService reportQrCodeService) {
        this.reportQrCodeService = reportQrCodeService;
    }

    public byte[] createPdf(LabTestRequest request) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();
            var registration = request.getLabOrder().getRegistration();
            document.add(new Paragraph("ApuxLabs Diagnostic Report"));
            document.add(new Paragraph("Patient: " + registration.getFirstName() + " " + (registration.getLastName() == null ? "" : registration.getLastName())));
            document.add(new Paragraph("UHID: " + (registration.getUhid() == null ? "—" : registration.getUhid())));
            document.add(new Paragraph("Patient ID: " + registration.getId() + "    Report: RPT-" + registration.getBarcode()));
            document.add(new Paragraph("Test: " + request.getTestMaster().getName() + " (" + request.getTestMaster().getCode() + ")"));
            document.add(new Paragraph("Verified by: " + (request.getVerifiedBy() == null ? "—" : request.getVerifiedBy())));
            addPathologistSection(document, request);
            addDownloadQrCode(document, registration.getReportDownloadToken());
            document.add(new Paragraph(" "));
            Table table = new Table(4);
            table.addCell(new Cell("Parameter")); table.addCell(new Cell("Result")); table.addCell(new Cell("Unit")); table.addCell(new Cell("Reference range"));
            if (request.getResultData() != null) {
                request.getResultData().forEach((key, value) -> {
                    if (key.equals("remarks") || key.equals("performedBy") || key.equals("verifiedBy") || key.equals("panelId") || key.equals("panelName") || key.equals("abnormalCount")) return;
                    table.addCell(new Cell(key)); table.addCell(new Cell(String.valueOf(value))); table.addCell(new Cell("")); table.addCell(new Cell(""));
                });
            }
            document.add(table);
            document.close();
            return output.toByteArray();
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate report PDF", exception);
        }
    }

    public byte[] createCombinedPdf(List<LabTestRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("No verified reports are available.");
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(new Paragraph("ApuxLabs Consolidated Diagnostic Report"));
            var registration = requests.get(0).getLabOrder().getRegistration();
            document.add(new Paragraph("Patient: " + registration.getFirstName() + " " + (registration.getLastName() == null ? "" : registration.getLastName())));
            document.add(new Paragraph("UHID: " + (registration.getUhid() == null ? "—" : registration.getUhid())));
            document.add(new Paragraph("Patient ID: " + registration.getId() + "    Visit: " + registration.getBarcode()));

            for (LabTestRequest request : requests) {
                document.newPage();
                document.add(new Paragraph(request.getTestMaster().getName() + " (" + request.getTestMaster().getCode() + ")"));
                document.add(new Paragraph("Verified by: " + (request.getVerifiedBy() == null ? "—" : request.getVerifiedBy())));
                addPathologistSection(document, request);
                addDownloadQrCode(document, registration.getReportDownloadToken());
                Table table = new Table(4);
                table.addCell(new Cell("Parameter")); table.addCell(new Cell("Result")); table.addCell(new Cell("Unit")); table.addCell(new Cell("Reference range"));
                if (request.getResultData() != null) {
                    request.getResultData().forEach((key, value) -> {
                        if (key.equals("remarks") || key.equals("performedBy") || key.equals("verifiedBy") || key.equals("panelId") || key.equals("panelName") || key.equals("abnormalCount")) return;
                        table.addCell(new Cell(key)); table.addCell(new Cell(String.valueOf(value))); table.addCell(new Cell("")); table.addCell(new Cell(""));
                    });
                }
                document.add(table);
            }
            document.close();
            return output.toByteArray();
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate consolidated report PDF", exception);
        }
    }

    private void addDownloadQrCode(Document document, UUID token) throws Exception {
        if (token == null) return;
        Image qrCode = Image.getInstance(reportQrCodeService.pngBytes(token.toString()));
        qrCode.scaleAbsolute(160, 160);
        qrCode.setBackgroundColor(Color.WHITE);
        qrCode.setAlignment(Image.ALIGN_CENTER);
        document.add(qrCode);
    }

    private void addPathologistSection(Document document, LabTestRequest request) {
        var pathologist = request.getPathologist();
        if (pathologist == null) return;
        document.add(new Paragraph("Pathologist: " + pathologist.getName()));
        document.add(new Paragraph("Designation: " + (pathologist.getDesignation() == null ? "—" : pathologist.getDesignation())));
        document.add(new Paragraph("Regd No: " + (pathologist.getRegistrationNumber() == null ? "—" : pathologist.getRegistrationNumber())));
        document.add(new Paragraph("Signature: ______________________________"));
    }
}

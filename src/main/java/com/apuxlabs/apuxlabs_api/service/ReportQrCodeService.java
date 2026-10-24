package com.apuxlabs.apuxlabs_api.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class ReportQrCodeService {
    private final String publicBaseUrl;

    public ReportQrCodeService(@Value("${app.public-base-url}") String publicBaseUrl) {
        this.publicBaseUrl = publicBaseUrl.replaceAll("/$", "");
    }

    public String downloadUrl(String token) {
        return publicBaseUrl + "/public/reports/download/" + token;
    }

    public String dataUrl(String token) {
        try {
            var matrix = new QRCodeWriter().encode(downloadUrl(token), BarcodeFormat.QR_CODE, 240, 240);
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(matrix, "PNG", output, new MatrixToImageConfig());
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(output.toByteArray());
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate report QR code", exception);
        }
    }
}

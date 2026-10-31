package com.apuxlabs.apuxlabs_api.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
import com.google.zxing.EncodeHintType;

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
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(pngBytes(token));
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate report QR code", exception);
        }
    }

    public byte[] pngBytes(String token) {
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.MARGIN, 2);
            var matrix = new QRCodeWriter().encode(downloadUrl(token), BarcodeFormat.QR_CODE, 320, 320, hints);
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(matrix, "PNG", output, new MatrixToImageConfig());
                return output.toByteArray();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not generate report QR code", exception);
        }
    }
}

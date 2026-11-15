package com.apuxlabs.apuxlabs_api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class ReportDownloadRateLimiter {
    private static final String UPSERT_SQL = """
        INSERT INTO report_download_rate_limit(rate_key, window_started_at, request_count)
        VALUES (?, CURRENT_TIMESTAMP, 1)
        ON CONFLICT (rate_key) DO UPDATE SET
            request_count = CASE
                WHEN report_download_rate_limit.window_started_at <= CURRENT_TIMESTAMP - INTERVAL '1 minute' THEN 1
                ELSE report_download_rate_limit.request_count + 1
            END,
            window_started_at = CASE
                WHEN report_download_rate_limit.window_started_at <= CURRENT_TIMESTAMP - INTERVAL '1 minute' THEN CURRENT_TIMESTAMP
                ELSE report_download_rate_limit.window_started_at
            END
        RETURNING request_count <= ?
        """;

    private final JdbcTemplate jdbcTemplate;
    private final int limitPerMinute;

    public ReportDownloadRateLimiter(JdbcTemplate jdbcTemplate,
                                     @Value("${app.report-download-rate-limit-per-minute:10}") int limitPerMinute) {
        this.jdbcTemplate = jdbcTemplate;
        this.limitPerMinute = Math.max(1, limitPerMinute);
    }

    public boolean tryConsume(String ipAddress, UUID reportToken) {
        String safeIp = ipAddress == null || ipAddress.isBlank() ? "unknown" : ipAddress;
        String tokenKey = hash(reportToken == null ? "missing" : reportToken.toString());
        return consume("ip:" + safeIp) && consume("token:" + tokenKey);
    }

    private boolean consume(String key) {
        Boolean allowed = jdbcTemplate.queryForObject(UPSERT_SQL, Boolean.class, key, limitPerMinute);
        return Boolean.TRUE.equals(allowed);
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    @Scheduled(fixedDelayString = "${app.report-download-rate-limit-cleanup-ms:3600000}")
    public void removeExpiredBuckets() {
        jdbcTemplate.update("DELETE FROM report_download_rate_limit WHERE window_started_at < CURRENT_TIMESTAMP - INTERVAL '1 day'");
    }
}

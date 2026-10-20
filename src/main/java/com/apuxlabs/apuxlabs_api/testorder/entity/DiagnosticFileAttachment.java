package com.apuxlabs.apuxlabs_api.testorder.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnostic_file_attachment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiagnosticFileAttachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_test_request_id", nullable = false)
    private LabTestRequest labTestRequest;
    @Column(nullable = false, length = 500)
    private String storagePath;
    @Column(nullable = false, length = 255)
    private String originalFileName;
    @Column(nullable = false, length = 150)
    private String contentType;
    private Long fileSize;
    @Column(nullable = false, length = 30)
    private String fileType;
    @Column(nullable = false, length = 100)
    private String uploadedBy;
    @Column(nullable = false)
    private LocalDateTime uploadedAt;
}

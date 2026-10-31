package com.apuxlabs.apuxlabs_api.testorder.repository;

import com.apuxlabs.apuxlabs_api.testorder.entity.DiagnosticFileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiagnosticFileAttachmentRepository extends JpaRepository<DiagnosticFileAttachment, Long> {
    List<DiagnosticFileAttachment> findByLabTestRequestIdOrderByUploadedAtDesc(Long labTestRequestId);
}

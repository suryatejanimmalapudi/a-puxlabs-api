package com.apuxlabs.apuxlabs_api.auth;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apuxlabs.apuxlabs_api.security.LabStaffPrincipal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/me")
    public AuthMeResponse me(@AuthenticationPrincipal LabStaffPrincipal principal) {
        List<String> roles = principal.getAuthorities().stream()
            .map(authority -> authority.getAuthority().replaceFirst("^ROLE_", ""))
            .toList();
        return new AuthMeResponse(principal.getEmail(), principal.getFullName(), principal.getLabId(), roles);
    }

    public record AuthMeResponse(String email, String fullName, String labId, List<String> roles) {}
}

package com.apuxlabs.apuxlabs_api.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.apuxlabs.apuxlabs_api.staff.entity.LabStaff;
import com.apuxlabs.apuxlabs_api.staff.repository.LabStaffRepository;

@Component
public class LabStaffGatekeeperFilter extends OncePerRequestFilter {

    private final LabStaffRepository labStaffRepository;

    public LabStaffGatekeeperFilter(LabStaffRepository labStaffRepository) {
        this.labStaffRepository = labStaffRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            Boolean emailVerified = jwt.getClaimAsBoolean("email_verified");

            if (email == null || email.isBlank() || !Boolean.TRUE.equals(emailVerified)) {
                writeForbidden(response);
                return;
            }

            LabStaff staff = labStaffRepository.findByEmailIgnoreCaseAndActiveTrue(email.trim()).orElse(null);
            if (staff == null) {
                writeForbidden(response);
                return;
            }

            LabStaffPrincipal principal = new LabStaffPrincipal(staff);
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, jwt, principal.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }

    private void writeForbidden(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"Unauthorized staff account\"}");
    }
}

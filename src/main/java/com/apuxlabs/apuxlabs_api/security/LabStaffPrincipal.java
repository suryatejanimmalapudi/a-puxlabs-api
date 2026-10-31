package com.apuxlabs.apuxlabs_api.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.apuxlabs.apuxlabs_api.staff.entity.LabStaff;

import lombok.Getter;

@Getter
public class LabStaffPrincipal implements UserDetails {

    private final String email;
    private final String labId;
    private final String fullName;
    private final Collection<? extends GrantedAuthority> authorities;

    public LabStaffPrincipal(LabStaff staff) {
        this.email = staff.getEmail();
        this.labId = staff.getLabId();
        this.fullName = staff.getFullName();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + staff.getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

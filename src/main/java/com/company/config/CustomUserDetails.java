package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Boolean visible;
    private GeneralStatus status;
    private GeneralRole role;


    public CustomUserDetails(ProfileEntity profile) {
        this.id = profile.getId();
        this.username = profile.getUsername();
        this.password = profile.getPassword();
        this.status = profile.getStatus();
        this.visible = profile.getVisible();
        this.role = profile.getRole();
    }
    public CustomUserDetails(CompanyEntity company) {
        this.id = company.getId();
        this.username = company.getUsername();
        this.password = company.getPassword();
        this.status = company.getStatus();
        this.visible = company.getVisible();
        this.role = company.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> roles = new LinkedList<>();
//        roles.add(new SimpleGrantedAuthority(profile.getRole().name()));
//        return roles;
        return new LinkedList<>(Collections.singletonList(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    public String getPassword() {
        return  password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return visible;
    }

    public String getId() {
        return id;
    }

    public GeneralRole getRole() {
        return role;
    }
}

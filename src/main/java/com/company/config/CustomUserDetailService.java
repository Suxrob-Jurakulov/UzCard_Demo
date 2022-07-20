package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.CompanyRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("uz_card")) {
            Optional<ProfileEntity> entity = profileRepository.findByUsername(username);
            if (entity.isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
            return new CustomUserDetails(entity.get());
        } else { // company
            Optional<CompanyEntity> optional = companyRepository.findByUsername(username);
            if (optional.isEmpty()) {
                throw new UsernameNotFoundException("Company Not Found");
            }
            return new CustomUserDetails(optional.get());
        }
    }
}

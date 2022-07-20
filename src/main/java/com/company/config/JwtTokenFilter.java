package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.ItemNotFoundException;
import com.company.service.CompanyService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CompanyService companyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();
            String id = JwtUtil.decode(token);
            UserDetails userDetails;
            ProfileEntity profile = profileService.get2(id);
            if (profile != null) {
                userDetails = new CustomUserDetails(profile);
            } else {
                CompanyEntity company = companyService.get2(id);
                if (company == null){
                    throw new ItemNotFoundException("Not found");
                }
                userDetails = new CustomUserDetails(company);
            }

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("Message", "Some Error");
            return;
        }
    }
}

package com.wbs.wbs.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.CustomUserDetails;
import com.wbs.wbs.entity.UserEntity;
import com.wbs.wbs.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String combined) throws UsernameNotFoundException {
        
        String[] parts = combined.split(":");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("형식 오류");
        }
        String name = parts[0];
        String militaryRank = parts[1];
        UserEntity user = userRepository.findByMilitaryRankAndName(militaryRank, name)
            .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        if ("Y".equalsIgnoreCase(user.getDelYn())) {
            throw new DisabledException("사용중지된 계정");
        }
            
        return new CustomUserDetails(user);
    } 
}

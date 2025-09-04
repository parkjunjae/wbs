package com.wbs.wbs.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wbs.wbs.dto.UserAdminDto;
import com.wbs.wbs.entity.UserEntity;
import com.wbs.wbs.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserRepository userRepository;


    public List<UserAdminDto.UserSummaryRes> listUser() {
        return userRepository.findAllByOrderByUserIdAsc().stream().map(u -> new UserAdminDto.UserSummaryRes(
            u.getUserId(),
            u.getName(),
            u.getMilitaryRank(),
            u.getRole(),
            u.getDelYn()
        ))
        .toList();
    }
    
    
    @Transactional
    public UserAdminDto.UserSummaryRes getUser(Long id) {
        UserEntity u = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        return new UserAdminDto.UserSummaryRes(
                u.getUserId(), u.getName(), u.getMilitaryRank(), u.getRole(), u.getDelYn()
        );
    }

    @Transactional
    public UserAdminDto.UserSummaryRes updateDel(Long id, String delYn) {
        UserEntity u = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        String v = (delYn == null ? "" : delYn).trim().toUpperCase();
        if (!v.equals("Y") && !v.equals("N")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delYn must be 'Y' or 'N'");
        }

        u.setDelYn(v);

        UserEntity saved = userRepository.save(u);

        return UserAdminDto.UserSummaryRes.from(saved);
    }
}

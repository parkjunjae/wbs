package com.wbs.wbs.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wbs.wbs.config.JwtUtil;
import com.wbs.wbs.dto.LoginRequest;
import com.wbs.wbs.dto.ProfileUpdateRequest;
import com.wbs.wbs.dto.UserAdminDto;
import com.wbs.wbs.dto.UserInfoResponse;
import com.wbs.wbs.entity.CustomUserDetails;
import com.wbs.wbs.entity.UserEntity;
import com.wbs.wbs.repository.UserRepository;
import com.wbs.wbs.service.UserAdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserAdminService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<UserEntity> useropt = userRepository.findByMilitaryRankAndName(req.getMilitaryRank(), req.getName());
        if (useropt.isEmpty()) {
            return ResponseEntity.status(401).body("사용자 없음");
        }
        UserEntity user = useropt.get();

        if ("Y".equalsIgnoreCase(user.getDelYn())) {
            return ResponseEntity.status(401).body("사용중지된 계정"); // or "disabled"
        }
        
        if (!passwordEncoder.matches(req.getPasswd(), user.getPasswd())) {
            return ResponseEntity.status(401).body("비밀번호 불일치");
        }

        String token = jwtUtil.generateToken(user.getName(), user.getRole(), user.getMilitaryRank());

        Map<String, String> body = Map.of("token", token);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody LoginRequest req) {
        if (userRepository.findByMilitaryRankAndName(req.getMilitaryRank(), req.getName()).isPresent()) {
            return ResponseEntity.status(409).body("이미 가입된 사용자입니다.");
        }
        UserEntity user = new UserEntity();
        user.setMilitaryRank(req.getMilitaryRank());
        user.setName(req.getName());
        user.setPasswd(passwordEncoder.encode(req.getPasswd()));
        user.setDelYn("N");
        user.setRole("USER");
        userRepository.save(user);

        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserEntity user = userRepository.findByMilitaryRankAndName(userDetails.getRank(), userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        UserInfoResponse dto = new UserInfoResponse();
        dto.setName(user.getName());
        dto.setMilitaryRank(user.getMilitaryRank());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ProfileUpdateRequest req) {
        UserEntity user = userRepository.findByMilitaryRankAndName(userDetails.getRank(), userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPasswd())) {
            return ResponseEntity.status(401).body("현재 비밀번호가 일치 하지않습니다.");
        }
        user.setName(req.getName());
        user.setMilitaryRank(req.getMilitaryRank());
        if (req.getNewPassword() != null && !req.getNewPassword().isBlank()) {
            user.setPasswd(passwordEncoder.encode(req.getNewPassword()));
        }
        userRepository.save(user);

        return ResponseEntity.ok().body("수정완료");
    }


    @GetMapping("/user")
    public ResponseEntity<List<UserAdminDto.UserSummaryRes>> list() {
        return ResponseEntity.ok(userService.listUser());
    }

    @PutMapping("/{id}/del-yn")
    public ResponseEntity<UserAdminDto.UserSummaryRes> putMethodName(@PathVariable Long id, @Valid @RequestBody UserAdminDto.UpdateDelYnReq req) {
        return ResponseEntity.ok(userService.updateDel(id, req.delYn()));
    }
    

}

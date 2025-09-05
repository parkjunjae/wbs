package com.wbs.wbs.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wbs.wbs.service.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailService customUserDetailService;


     @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String path = request.getServletPath(); // 컨텍스트 경로(/app 등) 제거된 경로
        if ("/login".equals(path) || "/error".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // 1) 토큰 헤더가 없으면 통과(퍼블릭 엔드포인트 등)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();

        try {
            // 2) 토큰 유효성 검증 실패 시 401
            if (!jwtUtil.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
                return;
            }

            // 3) 토큰에서 subject 추출
            //    주의: 여기 값이 CustomUserDetailService가 기대하는 값(예: "name:rank" 또는 loginId)과 일치해야 함
            final String subject = jwtUtil.getUsername(token); // 또는 getSubject(token)

            // 4) DB에서 최신 사용자 상태 로드 (delYn 포함)
            UserDetails ud = customUserDetailService.loadUserByUsername(subject);

            // 5) delYn=Y 이면 즉시 401
            if (!ud.isEnabled()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "disabled");
                return;
            }

            // 6) 정상일 때만 SecurityContext에 인증 세팅 (중복 세팅 방지)
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            // 7) 다음 필터로
            chain.doFilter(request, response);

        } catch (DisabledException | UsernameNotFoundException e) {
            // delYn=Y 또는 미존재 사용자 등
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
        }
    }
    
}

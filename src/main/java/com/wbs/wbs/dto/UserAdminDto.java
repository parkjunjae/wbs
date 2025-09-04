package com.wbs.wbs.dto;

import com.wbs.wbs.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserAdminDto {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class UserSummaryRes {
        private Long userId;
        private String name;
        private String militaryRank;
        private String role;
        private String delYn;

        public static UserSummaryRes from(UserEntity e) {
            return UserSummaryRes.builder()
                    .userId(e.getUserId())
                    .name(e.getName())
                    .militaryRank(e.getMilitaryRank())
                    .role(e.getRole())
                    .delYn(e.getDelYn())
                    .build();
        }
    }

    public record UpdateDelYnReq(
            @NotBlank String delYn
    ) {}
    
}

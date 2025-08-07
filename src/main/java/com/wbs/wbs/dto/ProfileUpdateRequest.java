package com.wbs.wbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
    private String name;
    private String militaryRank;
    private String currentPassword;
    private String newPassword;
}

package com.wbs.wbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String name;
    private String militaryRank;
    private String passwd;
    
}

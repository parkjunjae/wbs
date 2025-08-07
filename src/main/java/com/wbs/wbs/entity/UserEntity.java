package com.wbs.wbs.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String name;


    @NotBlank(message= "계급을 선택해주세요.")
    private String militaryRank;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String passwd;

    private String role;

    @Column(name="del_yn")
    private String delYn;

    
}

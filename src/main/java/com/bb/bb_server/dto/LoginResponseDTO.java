package com.bb.bb_server.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    private String accessToken;

    public LoginResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }

}

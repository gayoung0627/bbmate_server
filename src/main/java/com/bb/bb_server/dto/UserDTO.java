package com.bb.bb_server.dto;

import com.bb.bb_server.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String accessToken;
    private String email;
    private String password;
    private String username;
    private String nickname;
    private Role role;
    private String image_url;
}

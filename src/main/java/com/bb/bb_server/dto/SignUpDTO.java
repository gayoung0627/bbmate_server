package com.bb.bb_server.dto;

import com.bb.bb_server.domain.Role;
import com.bb.bb_server.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank(message="비밀번호를 재입력해주세요")
    private String confirmPassword;

    @NotBlank(message="이름을 입력해주세요")
    private String username;

    @NotBlank(message="닉네임을 입력해주세요")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;


    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .username(username)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

}

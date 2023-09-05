package com.bb.bb_server.validator;

import com.bb.bb_server.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPasswordValidator extends AbstractValidator<SignUpDTO>{
    @Override
    protected void doValidate(SignUpDTO dto, Errors errors) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "비밀번호 일치 오류", "비밀번호가 일치하지 않습니다.");
        }
    }
}

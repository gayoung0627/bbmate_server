package com.bb.bb_server.validator;

import com.bb.bb_server.dto.SignUpDTO;
import com.bb.bb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<SignUpDTO> {
    private final UserRepository userRepository;
    @Override
    protected void doValidate(SignUpDTO dto, Errors errors) {
        if(userRepository.existsByEmail(dto.getEmail())){
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용 중인 이메일입니다.");
        }
    }
}

package com.bb.bb_server.controller;

import com.bb.bb_server.dto.SignUpDTO;
import com.bb.bb_server.dto.UserDTO;
import com.bb.bb_server.service.UserService;
import com.bb.bb_server.validator.CheckEmailValidator;
import com.bb.bb_server.validator.CheckNicknameValidator;
import com.bb.bb_server.validator.CheckPasswordValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckPasswordValidator checkPasswordValidator;
    private final UserService userService;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
        binder.addValidators(checkPasswordValidator);
    }

    @ApiOperation(value = "Signup POST", notes="POST 방식으로 회원가입")
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMap(bindingResult));
        }
        if(!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())){
            bindingResult.rejectValue("password", "confirmPassword", "패스워드가 일치하지 않습니다.");
        }
        userService.create(signUpDTO);
        return ResponseEntity.ok(signUpDTO);
    }

    private Map<String, String> getErrorMap(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }

}

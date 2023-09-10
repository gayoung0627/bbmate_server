package com.bb.bb_server.controller;

import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.LoginRequestDTO;
import com.bb.bb_server.dto.SignUpDTO;
import com.bb.bb_server.dto.UserDTO;
import com.bb.bb_server.service.UserService;
import com.bb.bb_server.validator.CheckEmailValidator;
import com.bb.bb_server.validator.CheckNicknameValidator;
import com.bb.bb_server.validator.CheckPasswordValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMap(bindingResult));
        }
        if(!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())){
            bindingResult.rejectValue("password", "confirmPassword", "패스워드가 일치하지 않습니다.");
        }
        userService.create(signUpDTO);
        return ResponseEntity.ok(signUpDTO);
    }

    @ApiOperation(value="Signup email duplicate  check POST", notes = "POST 방식으로 이메일 중복확인")
    @GetMapping("/signup/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmailDuplication(email));
    }

    @ApiOperation(value="Signup nickname duplicate  check POST", notes = "POST 방식으로 닉네임 중복확인")
    @GetMapping("/signup/nickname/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    @ApiOperation(value = "Login POST", notes = "POST 방식으로 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            UserDTO userDTO = userService.login(loginRequestDTO);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Logout POST", notes="POST 방식으로 로그아웃" )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("{\"message\": \"Logout success\"}");
    }

    @ApiOperation(value = "Update User PUT", notes = "PUT 방식으로 사용자 정보 업데이트")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(userId, userDTO);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "User DELETE", notes="DELETE 방식으로 회원 탈퇴" )
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId, @RequestParam String password) {
        try {
            boolean passwordMatches = userService.checkPassword(userId, password);

            if(!passwordMatches){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Password does not match.\"}");
            }
            userService.deleteById(userId);
            return ResponseEntity.ok().body("{\"message\": \"User deletion successful.\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"User deletion failed.\"}");
        }
    }

    @ApiOperation(value = "Find User by Nickname GET", notes = "GET 방식으로 닉네임으로 회원 정보 찾기")
    @GetMapping("/findUser/{nickname}")
    public ResponseEntity<?> findUserByNickname(@PathVariable String nickname) {
        try {
            User user = userService.findUserByNickname(nickname);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found by nickname: " + nickname);
        }
    }

    // 파일로 따로 뺄건지 다시 생각해보기
    private Map<String, String> getErrorMap(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }



    @GetMapping("/login")
    public void getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
    }


}

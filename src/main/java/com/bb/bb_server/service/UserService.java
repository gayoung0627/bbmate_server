package com.bb.bb_server.service;

import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.LoginRequestDTO;
import com.bb.bb_server.dto.SignUpDTO;
import com.bb.bb_server.dto.UserDTO;
import com.bb.bb_server.repository.UserRepository;
import com.bb.bb_server.security.TokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public boolean checkEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User create(SignUpDTO signUpDTO){
        String encPassword = passwordEncoder.encode(signUpDTO.getPassword());
        String encConfirmPassword = passwordEncoder.encode(signUpDTO.getConfirmPassword());
        signUpDTO.setPassword(encPassword);
        signUpDTO.setConfirmPassword(encConfirmPassword);
        User user = signUpDTO.toEntity();
        return userRepository.save(user);
    }

    public UserDTO login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Email not registered."));

        String password = loginRequestDTO.getPassword();
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Incorrect password.");
        }
        String token = tokenProvider.createToken(user.getUsername());

        UserDTO userDTO = UserDTO.builder()
                .accessToken(token)
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .image_url(user.getImageUrl())
                .build();

        return userDTO;
    }

    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    public boolean checkPassword(Long userId, String password) {
        return userRepository.findById(userId)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }


}

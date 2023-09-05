package com.bb.bb_server.service;

import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.SignUpDTO;
import com.bb.bb_server.repository.UserRepository;
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

    public boolean checkEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User create(SignUpDTO signUpDTO){
        String encPassword = passwordEncoder.encode(signUpDTO.getPassword());
        signUpDTO.setPassword(encPassword);
        User user = signUpDTO.toEntity();
        return userRepository.save(user);
    }





}

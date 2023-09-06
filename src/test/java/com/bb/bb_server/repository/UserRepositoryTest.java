package com.bb.bb_server.repository;

import com.bb.bb_server.domain.Role;
import com.bb.bb_server.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){

        this.userRepository.deleteAll();

        User user1 = User.builder()
                .username("username1")
                .email("username1@email.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("user1")
                .imageUrl("image.jpg")
                .role(Role.USER)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .username("username2")
                .email("username2@email.com")
                .password(passwordEncoder.encode("5678"))
                .nickname("user2")
                .imageUrl("image2.jpg")
                .role(Role.USER)
                .build();
        userRepository.save(user2);

    }

    @Test
    @DisplayName("회원 추가 테스트")
    void insertUserTest(){
        User user3 = User.builder()
                .username("username3")
                .email("username3@email.com")
                .password(passwordEncoder.encode("5678"))
                .nickname("user3")
                .imageUrl("image3.jpg")
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user3);
        assertNotNull(savedUser);
    }


    @Test
    @DisplayName("회원 조회 테스트")
    void findByEmailTest() {
        User user = userRepository.findByEmail("username1@email.com").orElseThrow();
        assertNotNull(user);
        assertEquals("username1@email.com", user.getEmail());
    }

    @Test
    @DisplayName("모든 유저 조회 테스트")
    void findAllTest(){
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    @DisplayName("비밀번호 수정 테스트")
    void updatePasswordTest(){
        Optional<User> user = userRepository.findById(1L);
        user.ifPresent(selectUser->{
            selectUser.setPassword(passwordEncoder.encode("123456"));
        });

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUserTest(){
        Optional<User> user = userRepository.findById(1L);
        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });
    }

}
package com.bb.bb_server.service;

import com.bb.bb_server.repository.CommentRepository;
import com.bb.bb_server.repository.PostRepository;
import com.bb.bb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


}

package com.bb.bb_server.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime appliedAt;

    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

    @Builder
    public Meeting(Long id, User user, Post post, LocalDateTime appliedAt, MeetingStatus status) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.appliedAt = appliedAt;
        this.status = status;
    }
}
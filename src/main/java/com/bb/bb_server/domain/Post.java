package com.bb.bb_server.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @NotNull
    private int maxParticipants;

    @NotNull
    private String prefer;

    @NotNull
    private LocalDateTime gameDate;

    @Enumerated(EnumType.STRING)
    private Stadium stadium;

    @NotNull
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post (Long id,
                 String title,
                 PostStatus status,
                 int maxParticipants,
                 String prefer,
                 LocalDateTime gameDate,
                 Stadium stadium,
                 String content,
                 User user){
        this.id = id;
        this.title = title;
        this.status = status;
        this.maxParticipants = maxParticipants;
        this.prefer = prefer;
        this.gameDate = gameDate;
        this.stadium = stadium;
        this.content = content;
        this.user = user;
    }
    public void updatePost(String title, PostStatus status, int maxParticipants, String prefer, LocalDateTime gameDate, Stadium stadium, String content) {
        this.title = title;
        this.status = status;
        this.maxParticipants = maxParticipants;
        this.prefer = prefer;
        this.gameDate = gameDate;
        this.stadium = stadium;
        this.content = content;
    }

}

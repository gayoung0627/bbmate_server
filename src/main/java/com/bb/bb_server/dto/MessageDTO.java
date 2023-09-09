package com.bb.bb_server.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;
    private String content;
    private String senderNickName;
    private String receiverNickName;

}

package com.bb.bb_server.controller;

import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.MessageDTO;
import com.bb.bb_server.repository.UserRepository;
import com.bb.bb_server.service.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @ApiOperation(value = "메시지 작성", notes = "새로운 메시지 작성")
    @PostMapping("/write")
    public ResponseEntity<MessageDTO> writeMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO savedMessage = messageService.write(messageDTO);
        return ResponseEntity.ok(savedMessage);
    }

    @ApiOperation(value = "받은 메시지 조회", notes = "특정 사용자가 받은 메시지 조회")
    @GetMapping("/received/{userId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        List<MessageDTO> receivedMessages = messageService.receivedMessage(user);
        return ResponseEntity.ok(receivedMessages);
    }

    @ApiOperation(value = "받은 메시지 삭제", notes = "받은 메시지 삭제")
    @DeleteMapping("/received/{messageId}/{userId}")
    public ResponseEntity<String> deleteReceivedMessage(
            @PathVariable Long messageId,
            @PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        String result = messageService.deleteMessageByReceiver(messageId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "보낸 메시지 조회", notes = "특정 사용자가 보낸 메시지 조회")
    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        List<MessageDTO> sentMessages = messageService.sentMessages(user);
        return ResponseEntity.ok(sentMessages);
    }

    @ApiOperation(value = "보낸 메시지 삭제", notes = "보낸 메시지 삭제")
    @DeleteMapping("/sent/{messageId}/{userId}")
    public ResponseEntity<String> deleteSentMessage(
            @PathVariable Long messageId,
            @PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        String result = messageService.deleteSentMessage(messageId, user);
        return ResponseEntity.ok(result);
    }
}


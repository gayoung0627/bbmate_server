package com.bb.bb_server.service;

import com.bb.bb_server.domain.Message;
import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.MessageDTO;
import com.bb.bb_server.repository.MessageRepository;
import com.bb.bb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageDTO write(MessageDTO messageDTO) {
        User receiver = userRepository.findByNickname(messageDTO.getReceiverNickName())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        User sender = userRepository.findByNickname(messageDTO.getSenderNickName())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        Message message = Message.builder()
                .receiver(receiver)
                .sender(sender)
                .content(messageDTO.getContent())
                .deletedByReceiver(false)
                .deletedBySender(false)
                .build();

        messageRepository.save(message);
        return MessageDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderNickName(sender.getNickname())
                .receiverNickName(receiver.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> receivedMessage(User user) {
        List<Message> messages = messageRepository.findAllByReceiver(user);

        return messages.stream()
                .filter(message -> !message.isDeletedByReceiver())
                .map(message -> MessageDTO.builder()
                        .id(message.getId())
                        .content(message.getContent())
                        .senderNickName(message.getSender().getNickname())
                        .receiverNickName(message.getReceiver().getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteMessageByReceiver(long messageId, User user) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        if (!user.equals(message.getReceiver())) {
            throw new IllegalArgumentException("User information does not match.");
        }

        if (!message.isDeletedByReceiver()) {
            message.deleteByReceiver();

            if (message.isDeletedBySender()) {
                messageRepository.delete(message);
                return "Message deleted from both sides";
            } else {
                messageRepository.save(message);
                return "Message deleted from one side.";
            }
        } else {
            return "The message has already been deleted.";
        }
    }


    @Transactional(readOnly = true)
    public List<MessageDTO> sentMessages(User user) {
        List<Message> messages = messageRepository.findAllBySender(user);

        return messages.stream()
                .filter(message -> !message.isDeletedBySender())
                .map(message -> MessageDTO.builder()
                        .id(message.getId())
                        .content(message.getContent())
                        .senderNickName(message.getSender().getNickname())
                        .receiverNickName(message.getReceiver().getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteSentMessage(Long messageId, User user) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));

        if (!user.equals(message.getSender())) {
            throw new IllegalArgumentException("User information does not match.");
        }

        message.deleteBySender();
        if (message.isDeleted()) {
            messageRepository.delete(message);
            return "Deleted from both sides";
        }
        return "Deleted from one side";
    }
}
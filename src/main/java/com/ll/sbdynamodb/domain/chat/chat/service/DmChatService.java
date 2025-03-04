package com.ll.sbdynamodb.domain.chat.chat.service;

import com.ll.sbdynamodb.domain.chat.chat.entity.DmChatMessage;
import com.ll.sbdynamodb.domain.chat.chat.repository.DmChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DmChatService {
    private final DmChatMessageRepository dmChatMessageRepository;

    public DmChatMessage write(long chatRoomId, String message) {
        return dmChatMessageRepository.save(
                DmChatMessage
                        .builder()
                        .chatRoomId(chatRoomId)
                        .message(message)
                        .build()
        );
    }

    public List<DmChatMessage> findDmChatMessagesByChatRoomId(long chatRoomId) {
        return dmChatMessageRepository.findByChatRoomId(chatRoomId);
    }

    public List<DmChatMessage> findDmChatMessagesByChatRoomIdAndCreateDateStartsWith(long chatRoomId, String createDate) {
        return dmChatMessageRepository.findByChatRoomIdAndCreateDateStartsWith(chatRoomId, createDate);
    }
}

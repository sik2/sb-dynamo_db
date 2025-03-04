package com.ll.sbdynamodb.domain.chat.chat.controller;

import com.ll.sbdynamodb.domain.chat.chat.entity.DmChatMessage;
import com.ll.sbdynamodb.domain.chat.chat.service.DmChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat/messages")
@RequiredArgsConstructor
public class ApiV1ChatMessageController {
    private final DmChatService dmChatService;

    @GetMapping("/create/{chatRoomId}")
    @ResponseBody
    public DmChatMessage create(@PathVariable long chatRoomId, String message) {
        return dmChatService.write(chatRoomId, message);
    }

    @GetMapping("/{chatRoomId}")
    @ResponseBody
    public List<DmChatMessage> messages(@PathVariable long chatRoomId) {
        return dmChatService.findDmChatMessagesByChatRoomId(chatRoomId);
    }

    @GetMapping("/{chatRoomId}/{createDate}")
    @ResponseBody
    public List<DmChatMessage> messages(@PathVariable long chatRoomId, @PathVariable String createDate) {
        return dmChatService.findDmChatMessagesByChatRoomIdAndCreateDateStartsWith(chatRoomId, createDate);
    }
}

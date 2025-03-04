package com.ll.sbdynamodb.domain.chat.chat.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmChatMessage {
    private String id;
    private Long chatRoomId;
    private String createDate;
    private String message;

    @DynamoDbPartitionKey
    public Long getChatRoomId() {
        return chatRoomId;
    }

    @DynamoDbSortKey
    public String getCreateDate() {
        return createDate;
    }
}

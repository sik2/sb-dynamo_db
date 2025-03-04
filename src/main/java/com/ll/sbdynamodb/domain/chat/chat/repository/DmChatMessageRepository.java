package com.ll.sbdynamodb.domain.chat.chat.repository;

import com.ll.sbdynamodb.domain.chat.chat.entity.DmChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DmChatMessageRepository {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "chatMessage";
    private final TableSchema<DmChatMessage> schema = TableSchema.fromBean(DmChatMessage.class);

    public DmChatMessage save(DmChatMessage DmChatMessage) {
        DmChatMessage.setId(UUID.randomUUID().toString());
        DmChatMessage.setCreateDate(LocalDateTime.now().toString());

        DynamoDbTable<DmChatMessage> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(DmChatMessage.class));
        PutItemEnhancedRequest<DmChatMessage> putItemEnhancedRequest = PutItemEnhancedRequest.builder(DmChatMessage.class)
                .item(DmChatMessage)
                .build();

        table.putItem(putItemEnhancedRequest);

        return DmChatMessage;
    }

    public List<DmChatMessage> findByChatRoomId(long chatRoomId) {
        Key partitionKey = Key.builder().partitionValue(chatRoomId).build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(partitionKey);
        SdkIterable<Page<DmChatMessage>> pages = dynamoDbEnhancedClient.table(TABLE_NAME, schema)
                .query(queryConditional);

        List<DmChatMessage> results = new ArrayList<>();
        for (Page<DmChatMessage> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }

    public List<DmChatMessage> findByChatRoomIdAndCreateDateStartsWith(long chatRoomId, String createDate) {
        String createDateFrom = createDate + "T00:00:00.000000000";
        String createDateTo = createDate + "T23:59:59.999999999";

        QueryConditional queryConditional = QueryConditional
                .sortBetween(
                        Key.builder().partitionValue(chatRoomId).sortValue(createDateFrom).build(),
                        Key.builder().partitionValue(chatRoomId).sortValue(createDateTo).build()
                );

        SdkIterable<Page<DmChatMessage>> pages = dynamoDbEnhancedClient
                .table(TABLE_NAME, schema)
                .query(queryConditional);

        List<DmChatMessage> results = new ArrayList<>();
        for (Page<DmChatMessage> page : pages) {
            results.addAll(page.items());
        }

        return results;
    }
}

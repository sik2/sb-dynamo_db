package com.ll.sbdynamodb.domain.post.post.repository;

import com.ll.sbdynamodb.domain.post.post.entity.DmPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DmPostRepository {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private static final String TABLE_NAME = "post";

    public DmPost save(DmPost post) {
        post.setId(UUID.randomUUID().toString());
        post.setCreateDate(LocalDateTime.now().toString());

        DynamoDbTable<DmPost> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(DmPost.class));
        PutItemEnhancedRequest<DmPost> putItemEnhancedRequest = PutItemEnhancedRequest.builder(DmPost.class)
                .item(post)
                .build();

        table.putItem(putItemEnhancedRequest);

        return post;
    }

    // 운영모드에서는 사용하지 마세요.
    public List<DmPost> findAll() {
        DynamoDbTable<DmPost> table = dynamoDbEnhancedClient.table(TABLE_NAME, BeanTableSchema.create(DmPost.class));
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder().build();
        PageIterable<DmPost> scan = table.scan(scanEnhancedRequest);

        return scan.items().stream().toList();
    }

    public Optional<DmPost> findById(String id) {
        DynamoDbTable<DmPost> table = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(DmPost.class));

        Key key = Key.builder()
                .partitionValue(id)
                .build();

        DmPost post = table.getItem(
                (GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

        return Optional.ofNullable(post);
    }
}

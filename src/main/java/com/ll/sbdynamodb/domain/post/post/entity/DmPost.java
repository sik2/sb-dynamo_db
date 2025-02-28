package com.ll.sbdynamodb.domain.post.post.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmPost {
    private String id;
    private String createDate;
    private String subject;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}

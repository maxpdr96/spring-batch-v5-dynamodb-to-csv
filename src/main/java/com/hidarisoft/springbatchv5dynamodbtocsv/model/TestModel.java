package com.hidarisoft.springbatchv5dynamodbtocsv.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "test_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestModel {
    @DynamoDBHashKey(attributeName = "IdTest")
    private String idTest;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String cpf;
    @DynamoDBAttribute
    private String fullName;
}

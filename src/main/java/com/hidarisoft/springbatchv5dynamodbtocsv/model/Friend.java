package com.hidarisoft.springbatchv5dynamodbtocsv.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

@Data
@DynamoDBDocument
public class Friend {
    @DynamoDBAttribute
    private int id;
    @DynamoDBAttribute
    private String name;
}

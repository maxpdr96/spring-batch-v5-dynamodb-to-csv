package com.hidarisoft.springbatchv5dynamodbtocsv.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.hidarisoft.springbatchv5dynamodbtocsv.utils.StringSetConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@DynamoDBTable(tableName = "test_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestModel {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute
    private int index;
    @DynamoDBAttribute
    private String guid;
    @DynamoDBAttribute
    private boolean isActive;
    @DynamoDBAttribute
    private String balance;
    @DynamoDBAttribute
    private String picture;
    @DynamoDBAttribute
    private int age;
    @DynamoDBAttribute
    private String eyeColor;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String gender;
    @DynamoDBAttribute
    private String company;
    @DynamoDBAttribute
    private String email;
    @DynamoDBAttribute
    private String phone;
    @DynamoDBAttribute
    private String address;
    @DynamoDBAttribute
    private String about;
    @DynamoDBAttribute
    private String registered;
    @DynamoDBAttribute
    private double latitude;
    @DynamoDBAttribute
    private double longitude;
    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = StringSetConverter.class)
    private Set<String> tags;
    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
    private List<Friend> friends;
    @DynamoDBAttribute
    private String greeting;
    @DynamoDBAttribute
    private String favoriteFruit;
}

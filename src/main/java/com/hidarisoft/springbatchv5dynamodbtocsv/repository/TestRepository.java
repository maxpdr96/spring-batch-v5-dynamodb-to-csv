package com.hidarisoft.springbatchv5dynamodbtocsv.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<TestModel> getData() {

        //DynamoDBQueryExpression<TestModel> dynamoDBQueryExpression = new DynamoDBQueryExpression<TestModel>()
        //.withKeyConditionExpression("IdTest = :val")
        //.withExpressionAttributeValues(Map.of(":val", new AttributeValue().withS("1")));
        return dynamoDBMapper.scan(TestModel.class, new DynamoDBScanExpression());
    }
}

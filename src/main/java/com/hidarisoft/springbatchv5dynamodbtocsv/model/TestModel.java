package com.hidarisoft.springbatchv5dynamodbtocsv.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "test_table")
public class TestModel {
    @DynamoDBHashKey(attributeName = "IdTest")
    private String idTest;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String cpf;

    public TestModel() {
    }

    public TestModel(String idTest, String name, String cpf) {
        this.idTest = idTest;
        this.name = name;
        this.cpf = cpf;
    }

    public String getIdTest() {
        return idTest;
    }

    public void setIdTest(String idTest) {
        this.idTest = idTest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "idTest='" + idTest + '\'' +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}

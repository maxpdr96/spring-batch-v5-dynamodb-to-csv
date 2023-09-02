package com.hidarisoft.springbatchv5dynamodbtocsv.utils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.HashSet;
import java.util.Set;

public class StringSetConverter  implements DynamoDBTypeConverter<Set<String>, Set<String>> {

    @Override
    public Set<String> convert(Set<String> object) {
        return object;
    }

    @Override
    public Set<String> unconvert(Set<String> object) {
        if (object == null) {
            return null;
        }
        return new HashSet<>(object);
    }
}
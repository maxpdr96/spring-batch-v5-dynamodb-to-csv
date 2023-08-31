package com.hidarisoft.springbatchv5dynamodbtocsv.reader;


import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import com.hidarisoft.springbatchv5dynamodbtocsv.repository.TestRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBItemReader implements ItemReader<TestModel> {

    private TestRepository testRepository;
    private int currentRowIndex;

    public DynamoDBItemReader(TestRepository testRepository) {
        this.testRepository = testRepository;
        this.currentRowIndex = 0;
    }

    @Override
    public TestModel read() {
        var listData = testRepository.getData();
        if (currentRowIndex < listData.size()) {
            return listData.get(currentRowIndex++);
        } else {
            return null;
        }

    }
}

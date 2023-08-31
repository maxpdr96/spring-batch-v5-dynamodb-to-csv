package com.hidarisoft.springbatchv5dynamodbtocsv.writer;

import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TestItemWriter implements ItemWriter<TestModel> {

    @Override
    public void write(Chunk<? extends TestModel> chunk) throws Exception {
        System.out.println("Inside writer");

        chunk.getItems().forEach(testModel -> {
            System.out.println("teste:" + chunk.size());
            System.out.println(testModel);
        });
    }
}

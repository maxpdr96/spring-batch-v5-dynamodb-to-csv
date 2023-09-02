package com.hidarisoft.springbatchv5dynamodbtocsv.writer;

import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TestItemWriterTest {

    @InjectMocks
    private TestItemWriter testItemWriter;

    @Test
    void shouldRight() throws Exception {
        List<TestModel> testModels = new ArrayList<>();
        TestModel testModel = new TestModel();
        testModel.setIdTest("1");
        testModel.setCpf("12345678909");
        testModel.setName("Max");

        testModels.add(testModel);

        Chunk<TestModel> chunk = new Chunk<>(testModels);

        // Redirect the console output to a buffer for testing
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        testItemWriter.write(chunk);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Inside writer"));
        assertTrue(consoleOutput.contains("teste:1")); // Check the size of the chunk

    }

}
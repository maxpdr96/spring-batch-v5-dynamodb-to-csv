package com.hidarisoft.springbatchv5dynamodbtocsv.reader;

import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import com.hidarisoft.springbatchv5dynamodbtocsv.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DynamoDBItemReaderTest {

    @InjectMocks
    private DynamoDBItemReader dynamoDBItemReader;
    @Mock
    private TestRepository testRepository;

    @Test
    void shouldReturnValueModelExpectedWhenPayloadIsOk() {
        List<TestModel> testData = Arrays.asList(
                new TestModel("1", "Max", "12345678909"),
                new TestModel("2", "test", "1234567654"));

        // Mock the behavior of the testRepository
        when(testRepository.getData()).thenReturn(testData);

        var test = dynamoDBItemReader.read();

        assertAll("Test Model equals expected",
                () -> {
                    assert test != null;
                    assertEquals("1", test.getIdTest());
                },
                () -> {
                    assert test != null;
                    assertEquals("Max", test.getName());
                },
                () -> {
                    assert test != null;
                    assertEquals("12345678909", test.getCpf());
                });

    }

    @Test
    void shouldReturnNullExpectedWhenPayloadIsEmpty() {
        List<TestModel> testData = new ArrayList<>();

        // Mock the behavior of the testRepository
        when(testRepository.getData()).thenReturn(testData);

        var test = dynamoDBItemReader.read();

        assertNull(test);

    }

}
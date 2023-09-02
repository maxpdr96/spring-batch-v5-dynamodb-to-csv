package com.hidarisoft.springbatchv5dynamodbtocsv.config;

import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import com.hidarisoft.springbatchv5dynamodbtocsv.reader.DynamoDBItemReader;
import com.hidarisoft.springbatchv5dynamodbtocsv.writer.TestItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BatchConfiguration extends DefaultBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private DynamoDBItemReader dynamoDBItemReader;
    @Autowired
    private TestItemWriter testItemWriter;


    @Override
    protected DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, JobExecutionListener jobExecutionListener, Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step", jobRepository).
                <TestModel, TestModel>chunk(1, platformTransactionManager)
                .reader(dynamoDBItemReader)
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemWriter<TestModel> writer() {
        Class<TestModel> modelClass = TestModel.class;
        Field[] fields = modelClass.getDeclaredFields();
        List<String> propertyNames = new ArrayList<>();

        for (Field field : fields) {
            propertyNames.add(field.getName());
        }

        return new FlatFileItemWriterBuilder<TestModel>()
                .append(true)
                .name("TestItemWriter")
                .resource(new FileSystemResource("src/main/resources/test.csv"))
                .lineAggregator(item -> {
                    List<String> propertyValues = new ArrayList<>();
                    for (String propertyName : propertyNames) {
                        try {
                            Field field = modelClass.getDeclaredField(propertyName);
                            field.setAccessible(true);
                            Object value = field.get(item);
                            log.info("Values: " + value);
                            propertyValues.add(value != null ? value.toString() : "");
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            log.error("Error", e);
                        }
                    }
                    return String.join(",", propertyValues);
                })
                .delimited()
                .names(propertyNames.toArray(new String[0]))
                .headerCallback(writer -> writer.write(String.join(",", propertyNames)))
                .build();
    }

}

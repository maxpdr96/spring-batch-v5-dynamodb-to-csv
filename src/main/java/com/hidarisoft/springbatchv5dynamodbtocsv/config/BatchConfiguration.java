package com.hidarisoft.springbatchv5dynamodbtocsv.config;

import com.hidarisoft.springbatchv5dynamodbtocsv.model.TestModel;
import com.hidarisoft.springbatchv5dynamodbtocsv.reader.DynamoDBItemReader;
import com.hidarisoft.springbatchv5dynamodbtocsv.writer.TestItemWriter;
import org.springframework.batch.core.Job;
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

@Configuration
public class BatchConfiguration extends DefaultBatchConfiguration {

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
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
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
        return new FlatFileItemWriterBuilder<TestModel>()
                .append(true)
                .name("TestItemWriter")
                .resource(new FileSystemResource("src/main/resources/test.csv"))
                .lineAggregator(item -> item.getIdTest() + "," + item.getName() + "," + item.getCpf())
                .delimited()
                .names("idTest", "name", "cpf")
                .headerCallback(writer -> writer.write("idTest, name, cpf"))
                .build();
    }

}

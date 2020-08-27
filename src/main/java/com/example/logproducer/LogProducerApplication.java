package com.example.logproducer;


import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yandex.clickhouse.ClickHouseConnectionImpl;
import ru.yandex.clickhouse.ClickHouseDataSource;

import java.sql.SQLException;

@SpringBootApplication
@CommonsLog(topic = "App")
public class LogProducerApplication {
    @Value("${topic.name}")
    private String topicName;

    @Value("${topic.partitions-num}")
    private Integer partitions;

    @Value("${topic.replication-factor}")
    private short replicationFactor;

    @Value("${spring.datasource.third.jdbc-url}")
    private String DB_URL;

    public static void main(String[] args) {
        SpringApplication.run(LogProducerApplication.class, args);
    }

    @Bean
    NewTopic loggingTopic(){
        return new NewTopic(topicName, partitions, replicationFactor);
    }

    @Bean(name="clickhouse_connection")
    ClickHouseConnectionImpl getConnection() throws SQLException {
        ClickHouseDataSource dataSource = new ClickHouseDataSource(DB_URL);
        return (ClickHouseConnectionImpl) dataSource.getConnection();
    }

}

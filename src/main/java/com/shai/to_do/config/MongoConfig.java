package com.shai.to_do.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.shai.to_do.repository.TodoRepositoryCustomImpl;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(repositoryBaseClass = TodoRepositoryCustomImpl.class)
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected @NonNull String getDatabaseName() {
        return "todos";
    }

    @Override
    public @NonNull MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}


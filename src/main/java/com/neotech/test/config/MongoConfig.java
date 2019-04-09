package com.neotech.test.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${base.connectionString}")
    private String uri;

    @Value("${base.name}")
    private String dataBaseName;

    @Bean
    @Override
    public MongoClient mongoClient() {
        MongoClientOptions.Builder options = MongoClientOptions.builder()
                .connectTimeout(5000)
                .socketTimeout(5000)
                .maxWaitTime(5000);
        MongoClientURI mongoClientURI = new MongoClientURI(uri, options);
        return new MongoClient(mongoClientURI);
    }

    @Override
    protected String getDatabaseName() {
        return dataBaseName;
    }
}


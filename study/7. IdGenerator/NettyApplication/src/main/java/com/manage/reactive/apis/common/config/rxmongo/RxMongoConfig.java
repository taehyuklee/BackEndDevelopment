package com.manage.reactive.apis.common.config.rxmongo;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import java.util.concurrent.TimeUnit;

@Configuration
public class RxMongoConfig extends AbstractReactiveMongoConfiguration {

    @Autowired
    private MongoProperties properties;

    @Override
    public MongoClient reactiveMongoClient() {

//        MongoCredential credential = MongoCredential.createCredential(properties.getUsername()
//                , properties.getDatabase()
//                , properties.getPassword());

//		ConnectionString conn = new ConnectionString(String.format("mongodb://%s:%s", properties.getHost(), properties.getPort()));
        ConnectionString conn = new ConnectionString(properties.getUri());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(conn)
//                .credential(credential)
                .applyToConnectionPoolSettings(builder ->
                        builder.minSize(10)
                                .maxSize(100)
                                .maxWaitTime(10, TimeUnit.SECONDS).build())
                .readPreference(ReadPreference.nearest())
                .codecRegistry(MongoClientSettings.getDefaultCodecRegistry())
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(100, TimeUnit.MILLISECONDS)
                                .readTimeout(100, TimeUnit.MILLISECONDS).build())
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(0, TimeUnit.MILLISECONDS).build())
                .writeConcern(WriteConcern.UNACKNOWLEDGED)
                .retryReads(false)
                .retryWrites(false)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return "RxTest";
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}

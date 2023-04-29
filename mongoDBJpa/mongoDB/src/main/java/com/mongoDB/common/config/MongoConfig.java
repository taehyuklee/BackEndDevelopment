// package com.mongoDB.common.config;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.TimeUnit;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.mongo.MongoProperties;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.MongoDatabaseFactory;
// import org.springframework.data.mongodb.config.EnableMongoAuditing;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

// import com.mongodb.ConnectionString;
// import com.mongodb.MongoClientSettings;
// import com.mongodb.MongoCredential;
// import com.mongodb.ReadPreference;
// import com.mongodb.ServerAddress;
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;

// import lombok.extern.slf4j.Slf4j;


 
// @Configuration
// @EnableMongoAuditing
// @Slf4j
// public class MongoConfig {

// 	@Autowired
// 	private MongoProperties properties;

// 	public MongoConfig() {

// 		log.info("MongoConfig() Created");
// 	}

// 	@Bean
// 	public MongoDatabaseFactory mongoDbFactory() {

// 		SimpleMongoClientDatabaseFactory mongo = new SimpleMongoClientDatabaseFactory(mongoClient(), properties.getDatabase());
// 		return mongo;

// 	}

// 	@Bean
// 	public MongoClient mongoClient() {

// 		List<ServerAddress> serverAddrList = new ArrayList<>();

// 		MongoCredential credential = MongoCredential.createCredential(properties.getUsername()
// 																		, properties.getDatabase()
// 																		, properties.getPassword());

// 		ConnectionString conn = new ConnectionString(String.format("mongodb://%s:%s", properties.getHost(), properties.getPort()));
// 		MongoClientSettings settings = MongoClientSettings.builder()
// 											.applyConnectionString(conn)
// 											// .applyToClusterSettings(builder ->
// 																// builder.hosts(null))
// 											.credential(credential)
// 											.applyToConnectionPoolSettings(builder ->
// 																builder.minSize(10)
// 																.maxSize(50)
// 																.maxWaitTime(10, TimeUnit.SECONDS))
// 											.readPreference(ReadPreference.secondary())
// 											.build();

// 		MongoClient mongoClient = MongoClients.create(settings);
// 		return mongoClient;
// 	}


// 	@Bean
// 	public MongoTemplate mongoTemplate() {
// 		return new MongoTemplate(mongoClient(), properties.getDatabase());
// 	}



// }

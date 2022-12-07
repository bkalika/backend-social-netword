package com.bkalika.socialnetwork.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

/**
 * @author @bkalika
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.bkalika.socialnetwork.repositories")
public class MongoClientConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "socialnetworkdb";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.credential(MongoCredential.createCredential("bohdan", "socialnetworkdb", "1".toCharArray()))
                .applyToClusterSettings(settings ->
                        settings.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))));
    }
}

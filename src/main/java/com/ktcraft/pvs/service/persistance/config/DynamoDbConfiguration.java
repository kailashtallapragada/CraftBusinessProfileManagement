package com.ktcraft.pvs.service.persistance.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DynamoDbConfiguration {

    @Value("${dynamo.endpoint}")
    private String dynamoEndpoint;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {

        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamoEndpoint, Regions.DEFAULT_REGION.getName()))
                .withClientConfiguration(buildDynamoClientConfiguration())
                .build();
    }

    private ClientConfiguration buildDynamoClientConfiguration() {
        return new ClientConfiguration()
                .withConnectionTimeout(2000)
                .withClientExecutionTimeout(2000)
                .withRequestTimeout(2000)
                .withSocketTimeout(2000)
                .withRetryPolicy(PredefinedRetryPolicies
                        .getDynamoDBDefaultRetryPolicyWithCustomMaxRetries(2));
    }
}

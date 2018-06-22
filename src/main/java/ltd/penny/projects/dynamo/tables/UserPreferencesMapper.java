package ltd.penny.projects.dynamo.tables;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class UserPreferencesMapper implements Mapper {
    private DynamoDBMapper mapper;

    public UserPreferencesMapper(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public UserPreferencesMapper(String awsAccessKeyId, String awsSecretAccessKey)  {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion("eu-west-1")
                .withCredentials(credentialsProvider)
                .build();

        mapper = new DynamoDBMapper(client);
    }

    @Override
    public void create(TableEntity tableEntity) {
        mapper.save(tableEntity);
    }

    @Override
    public void delete(TableEntity tableEntity) {
        mapper.delete(tableEntity);
    }
}

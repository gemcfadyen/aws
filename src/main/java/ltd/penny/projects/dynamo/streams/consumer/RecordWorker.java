package ltd.penny.projects.dynamo.streams.consumer;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.streamsadapter.AmazonDynamoDBStreamsAdapterClient;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import ltd.penny.projects.config.Configuration;

public class RecordWorker {

    public Worker createWorker(Configuration configuration) {
        //AWS Keys from the user associated with the table we are watching for changes - ie we will get an event come in for every change to this table
        AWSCredentials sourceTableCredentials = new BasicAWSCredentials(configuration.getAwsAccessKeyIdForSourceData(), configuration.getAwsSecretAccessKeyForSourceData());

        //AWS Keys for the user which will create and maintain the offset table (may be in a different AWS instance)
        AWSCredentials offsetTableCredentials = new BasicAWSCredentials(configuration.getAwsAccessKeyIdForOffsetTable(), configuration.getAwsSecretAccessKeyForOffsetTable());
        AWSCredentialsProvider streamAwsProvider = new AWSStaticCredentialsProvider(sourceTableCredentials);
        AWSCredentialsProvider ourDynamoProvider = new AWSStaticCredentialsProvider(offsetTableCredentials);

        AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withCredentials(ourDynamoProvider)
                .withRegion(Regions.EU_WEST_1)
                .build();

        AmazonCloudWatch cloudWatchClient = AmazonCloudWatchClientBuilder.standard().withCredentials(ourDynamoProvider)
                .withRegion(Regions.EU_WEST_1)
                .build();

       final KinesisClientLibConfiguration config = new KinesisClientLibConfiguration(configuration.getOffsetTableName(),
                configuration.getStreamName(),
                streamAwsProvider,
                "worker-id")
                .withMaxRecords(100)
               // .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON)
                .withIdleTimeBetweenReadsInMillis(500);

        AmazonKinesis adapterClient = new AmazonDynamoDBStreamsAdapterClient(sourceTableCredentials, new ClientConfiguration());
        adapterClient.setRegion(Region.getRegion(Regions.fromName(Regions.EU_WEST_1.getName())));

        final IRecordProcessorFactory recordProcessorFactory = new RecordProcessorFactory();
        return new Worker.Builder()
                .recordProcessorFactory(recordProcessorFactory)
                .config(config)
                .kinesisClient(adapterClient)
                .dynamoDBClient(dynamoDBClient)
                .cloudWatchClient(cloudWatchClient)
                .build();
    }
}

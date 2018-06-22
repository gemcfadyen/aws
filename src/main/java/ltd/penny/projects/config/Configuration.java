package ltd.penny.projects.config;

public interface Configuration {
    String getAwsAccessKeyId();
    String getAwsSecretAccessKey();
    String getAwsAccessKeyIdForOffsetTable();
    String getAwsSecretAccessKeyForOffsetTable();
    String getAwsAccessKeyIdForSourceData();
    String getAwsSecretAccessKeyForSourceData();
    String getOffsetTableName();
    String getStreamName();
}

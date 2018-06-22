package ltd.penny.projects.dynamo.tables;

import ltd.penny.projects.config.CommandLineArguments;
import ltd.penny.projects.config.ConfigFile;
import ltd.penny.projects.config.Configuration;

public class Application {
    public static void main(String... args) {
        CommandLineArguments commandLineArguments = new CommandLineArguments();
        Configuration config = new ConfigFile(commandLineArguments.getPathToConfig(args));
        String accessKey = config.getAwsAccessKeyId();
        String secretAccessKey = config.getAwsSecretAccessKey();

        UserPreferencesMapper mapper = new UserPreferencesMapper(accessKey, secretAccessKey);
        TableEntityFactory tableEntityFactory = new UserPreferencesFactory();
        DynamoDBRepository repository = new DynamoDBRepository(mapper, tableEntityFactory);
        repository.create("uuid-gm-test");
        repository.delete("uuid-gm-test");
    }
}

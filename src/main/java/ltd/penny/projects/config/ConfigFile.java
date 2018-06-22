package ltd.penny.projects.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class ConfigFile implements Configuration {
    public static final String PROVIDE_DEFAULT_VALUE = ":-";
    private Map<String, String> config;


    public ConfigFile(String absolutePathToConfigFile) {
        File configFile = new File(absolutePathToConfigFile);
        Yaml yaml = new Yaml();
        try {
            config = (Map<String, String>) yaml.load(new FileReader(configFile));
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("config file at [" + absolutePathToConfigFile + "] not found", e);
        }
    }

    @Override
    public String getAwsAccessKeyId() {
        return extractStringConfigValueFor("aws_access_key_id");
    }

    @Override
    public String getAwsSecretAccessKey() {
        return extractStringConfigValueFor("aws_secret_access_key");
    }

    @Override
    public String getAwsAccessKeyIdForOffsetTable() {
        return extractStringConfigValueFor("aws_access_key_id_for_offset_table");
    }

    @Override
    public String getAwsSecretAccessKeyForOffsetTable() {
        return extractStringConfigValueFor("aws_secret_access_key_for_offset_table");
    }

    @Override
    public String getAwsAccessKeyIdForSourceData() {
        return extractStringConfigValueFor("aws_access_key_id_for_source_data");
    }

    @Override
    public String getAwsSecretAccessKeyForSourceData() {
        return extractStringConfigValueFor("aws_secret_access_key_for_source_data");
    }

    @Override
    public String getOffsetTableName() {
        return extractStringConfigValueFor("offset_table_name");
    }

    @Override
    public String getStreamName() {
        return extractStringConfigValueFor("stream_name");
    }

    private String extractStringConfigValueFor(String key) {
        String configEntry = config.get(key);
        if (hasDefault(configEntry)) {
            return useDefault(configEntry);
        } else if (hasOnlyEnvironmentVariable(configEntry)) {
            return useEnvironmentVariable(configEntry);
        } else {
            return configEntry;
        }
    }

    private boolean hasDefault(String configEntry) {
        return hasOnlyEnvironmentVariable(configEntry) && configEntry.contains(":-");
    }

    private String useDefault(String configEntry) {
        return configEntry.substring(configEntry.indexOf(PROVIDE_DEFAULT_VALUE) + PROVIDE_DEFAULT_VALUE.length(), configEntry.indexOf("}"));
    }

    private String useEnvironmentVariable(String configEntry) {
        String environmentVariableName = configEntry.substring(configEntry.indexOf("${") + 2,
                configEntry.length() - 1);
        return System.getenv().get(environmentVariableName);
    }

    private boolean hasOnlyEnvironmentVariable(String userProfileSvcApiHeaderValue) {
        return userProfileSvcApiHeaderValue.startsWith("${");
    }
}

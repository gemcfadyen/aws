package ltd.penny.projects.config;

public class ConfigFileNotFoundException extends RuntimeException {
    public ConfigFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

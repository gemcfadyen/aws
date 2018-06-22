package ltd.penny.projects.config;

public class MissingCommandLineArgumentException extends RuntimeException {
    public MissingCommandLineArgumentException(String message) {
        super(message);
    }
}

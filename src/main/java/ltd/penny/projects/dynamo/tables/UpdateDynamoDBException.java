package ltd.penny.projects.dynamo.tables;

public class UpdateDynamoDBException extends RuntimeException {

    public UpdateDynamoDBException(String message, Throwable cause) {
       super(message, cause);
    }
}

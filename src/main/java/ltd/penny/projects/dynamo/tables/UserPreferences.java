package ltd.penny.projects.dynamo.tables;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


//Test table which stores those users whose holiday accommodation preferences is hotel

@DynamoDBTable(tableName = "user-preferences")
public class UserPreferences implements TableEntity {
    private String id;
    private String preference;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
       return id;
    }

    @DynamoDBRangeKey(attributeName = "preference")
    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public void setId(String id) {
        this.id = id;
    }
}

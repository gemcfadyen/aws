package ltd.penny.projects.dynamo.tables;

public class UserPreferencesFactory implements TableEntityFactory {
    private static final String ACCOMMODATION_TYPE = "hotel";

    public UserPreferences create(String userId) {
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setPreference(ACCOMMODATION_TYPE);
        userPreferences.setId(userId);
        return userPreferences;
    }
}

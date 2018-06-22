package ltd.penny.projects.dynamo.tables;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserPreferencesFactoryTest {
    @Test
    public void createsUserPreferences() {
        UserPreferencesFactory factory = new UserPreferencesFactory();
        UserPreferences userPreferences = factory.create("uuid1");

        assertThat(userPreferences.getId(), is("uuid1"));
        assertThat(userPreferences.getPreference(), is("hotel"));
    }
}

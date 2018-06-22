package ltd.penny.projects.dynamo.tables;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class UserPreferencesMapperTest {
    @Mock
    private DynamoDBMapper dynamoMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userPreferencesMapperDeletesUser() {
        UserPreferences userPreferencesEntity = new UserPreferencesFactory().create("uuid-1");
        UserPreferencesMapper userPreferencesMapper = new UserPreferencesMapper(dynamoMapper);
        userPreferencesMapper.delete(userPreferencesEntity);

        verify(dynamoMapper).delete(userPreferencesEntity);
    }
}

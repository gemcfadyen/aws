package ltd.penny.projects.dynamo.tables;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DynamoDBRepositoryTest {
    @Test
    public void factoryCreatesUserPreferenceForDeletion() {
        DynamoObjectMapperSpy objectMapper = new DynamoObjectMapperSpy();
        UserPreferenceFactory factory = new UserPreferenceFactory();
        DynamoDBRepository dynamoDBRepository = new DynamoDBRepository(objectMapper, factory);

        dynamoDBRepository.delete("uuid-1");

        assertThat(factory.hasCreatedEntity(), is(true));
    }

    @Test
    public void userPreferenceIsDeleted() {
        DynamoObjectMapperSpy objectMapper = new DynamoObjectMapperSpy();
        UserPreferenceFactory factory = new UserPreferenceFactory();
        DynamoDBRepository dynamoDBRepository = new DynamoDBRepository(objectMapper, factory);

        dynamoDBRepository.delete("uuid-1");

        assertThat(objectMapper.hasDeleted(), is(true));
        assertThat(objectMapper.getUserPreferences().getId(), is("uuid-1"));
        assertThat(objectMapper.getUserPreferences().getPreference(), is("hotel"));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void exceptionThrownWhenIssueDeletingFromDynamo() {
        expectedException.expect(Matchers.instanceOf(UpdateDynamoDBException.class));
        expectedException.expectCause(Matchers.instanceOf(AmazonDynamoDBException.class));
        expectedException.expectMessage("Error deleting userId=uuid-1 to Dynamo");

        DynamoMapperThrowsExceptionStub objectMapper = new DynamoMapperThrowsExceptionStub();
        UserPreferenceFactory factory = new UserPreferenceFactory();
        DynamoDBRepository dynamoDBRepository = new DynamoDBRepository(objectMapper, factory);

        dynamoDBRepository.delete("uuid-1");
    }

    @Test
    public void createsEntityInDynamo() {
        DynamoObjectMapperSpy objectMapper = new DynamoObjectMapperSpy();
        UserPreferenceFactory factory = new UserPreferenceFactory();
        DynamoDBRepository dynamoDBRepository = new DynamoDBRepository(objectMapper, factory);

        dynamoDBRepository.create("uuid-1");

        assertThat(objectMapper.hasCreated(), is(true));
        assertThat(objectMapper.getUserPreferences().getId(), is("uuid-1"));
        assertThat(objectMapper.getUserPreferences().getPreference(), is("hotel"));
    }

    @Test
    public void exceptionThrownWhenIssueWritingToDynamo() {
        expectedException.expect(Matchers.instanceOf(UpdateDynamoDBException.class));
        expectedException.expectCause(Matchers.instanceOf(AmazonDynamoDBException.class));
        expectedException.expectMessage("Error writing userId=uuid-1 to Dynamo");

        DynamoMapperThrowsExceptionStub objectMapper = new DynamoMapperThrowsExceptionStub();
        UserPreferenceFactory factory = new UserPreferenceFactory();
        DynamoDBRepository dynamoDBRepository = new DynamoDBRepository(objectMapper, factory);

        dynamoDBRepository.create("uuid-1");
    }
}

class UserPreferenceFactory implements TableEntityFactory {
    private boolean hasCreatedEntity = false;

    public UserPreferences create(String userId) {
        hasCreatedEntity = true;
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setId(userId);
        userPreferences.setPreference("hotel");
        return userPreferences;
    }

    public boolean hasCreatedEntity() {
        return hasCreatedEntity;
    }
}

class DynamoObjectMapperSpy implements Mapper {
    private boolean hasDeleted = false;
    private boolean hasCreated = false;
    private UserPreferences userPreferences;

    @Override
    public void create(TableEntity tableEntity) {
        hasCreated = true;
        userPreferences = (UserPreferences) tableEntity;
    }

    @Override
    public void delete(TableEntity candidateForDeletion) {
        hasDeleted = true;
        userPreferences = (UserPreferences) candidateForDeletion;
    }

    public boolean hasDeleted() {
        return hasDeleted;
    }

    public boolean hasCreated() {
        return hasCreated;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }
}

class DynamoMapperThrowsExceptionStub implements Mapper {

    @Override
    public void create(TableEntity tableEntity) {
        throw new AmazonDynamoDBException("error for test");
    }

    @Override
    public void delete(TableEntity candidateForDeletion) {
        throw new AmazonDynamoDBException("error for test");
    }
}

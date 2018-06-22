package ltd.penny.projects.dynamo.tables;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamoDBRepository {
    private Mapper mapper;
    private TableEntityFactory tableEntityFactory;

    public DynamoDBRepository(Mapper mapper, TableEntityFactory tableEntityFactory) {
        this.mapper = mapper;
        this.tableEntityFactory = tableEntityFactory;
    }

    public void create(String userId) {
        try {
            TableEntity tableEntity = tableEntityFactory.create(userId);
            mapper.create(tableEntity);
            log.info("Successfully created userId=" + userId + " to Dynamo");
        } catch (Exception e) {
            log.error("Error writing userId=" + userId + " to Dynamo");
            throw new UpdateDynamoDBException("Error writing userId=" + userId + " to Dynamo", e);
        }

    }

    public void delete(String userId) {
        try {
            TableEntity tableEntity = tableEntityFactory.create(userId);
            mapper.delete(tableEntity);
            log.info("Successfully updated userId=" + userId + " to Dynamo");
        } catch (Exception e) {
            log.error("Error writing userId=" + userId + " to Dynamo");
            throw new UpdateDynamoDBException("Error deleting userId=" + userId + " to Dynamo", e);
        }
    }
}

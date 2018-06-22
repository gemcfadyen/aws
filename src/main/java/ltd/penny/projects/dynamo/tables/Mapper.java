package ltd.penny.projects.dynamo.tables;

public interface Mapper {
    void create(TableEntity tableEntity);
    void delete(TableEntity tableEntity);
}

package Netology.model;

public class OperationID {

    private String id;
    public OperationID(String operationId) {
        this.id = operationId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OperationID{" +
                "id='" + id + '\'' +
                '}';
    }
}

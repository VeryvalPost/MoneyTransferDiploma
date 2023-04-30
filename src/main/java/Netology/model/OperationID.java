package Netology.model;

public class OperationID {

    private String id;
    private boolean success;
    public OperationID(String operationId, boolean success) {

        this.id = operationId;
        this.success = success;
    }

    public String getId() {
        return id;
    }
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "OperationID{" +
                "id='" + id + '\'' +
                "success='" + success + '\'' +
                '}';
    }
}

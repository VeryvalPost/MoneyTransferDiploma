package netology.model;

public class OperationID {

    private String operationId;
    private boolean success;

    public OperationID(String operationId) {

        this.operationId = operationId;
    }

    public String getId() {
        return operationId;
    }

    @Override
    public String toString() {
        return "OperationID{" +
                "id='" + operationId + '\'' +
                '}';
    }
}

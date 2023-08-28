package Utils.OperationResults;

public class IdResult extends OperationResult {
    private final int id;

    public IdResult(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

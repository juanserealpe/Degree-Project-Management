package Utilities;
import java.util.List;
import java.util.Map;

/**
 * Base class representing the result of an operation.
 *
 * @author Alexp
 *
 * Stores whether the operation was successful and a descriptive message.
 * Can be extended to include more detailed information for specific cases.
 */
public class DbOperationResult {
    private final boolean success;
    private final String message;
    private final int rowsAffected;
    private final List<Map<String, Object>> payload;

    private DbOperationResult(boolean pSuccess, String pMessage, List<Map<String, Object>> pPayload, int pRowsAffected) {
        this.success = pSuccess;
        this.message = pMessage;

        this.payload = pPayload;
        this.rowsAffected = pRowsAffected;
    }
    //CREATORS//
    public static DbOperationResult createSuccessfulInsertResult(int pRowsAffected){
        return new DbOperationResult(true, Messages.getMessageSuccessfulInsertWithRowsAffected(pRowsAffected), null,pRowsAffected);
    }
    public static DbOperationResult createSuccessfulUpdateResult(int pRowsAffected){
        return new DbOperationResult(true, Messages.getMessageSuccessfulUpdateWithRowsAffected(pRowsAffected),null,pRowsAffected);
    }
    public static DbOperationResult createSuccessfulRetrieveResult(List<Map<String, Object>> pPayload){
        return new DbOperationResult(true, Messages.getMessageSuccessfulRetrieveWithCountRows(pPayload.size()),pPayload,0);
    }
    public static DbOperationResult createFailedInsertResult(){
        return new DbOperationResult(false, Messages.getMessageFailedInsert(),null,0);
    }
    public static DbOperationResult createFailedUpdateResult(){
        return new DbOperationResult(false, Messages.getMessageFailedUpdate(),null,0);
    }
    public static DbOperationResult createFailedRetrieveResult(){
        return new DbOperationResult(false, Messages.getMessageFailedRetrieve(),null,0);
    }
    public static DbOperationResult CreateErrorExceptionResult(String pMessage){
        return new DbOperationResult(false,pMessage, null,0);
    }
    public static DbOperationResult CreateUnconfiguredResult(){
        return new DbOperationResult(false,"Unconfigured", null,0);
    }
    public int getRowsExtracted(){
        if(payload == null) return 0;
        else return payload.size();
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Map<String, Object>> getPayload() { return payload; }
    public int getRowsAffected() { return rowsAffected; }

    // IN PROCESS OF CREATION XD
    public boolean isQuery() { return rowsAffected == 0 && success; }
    public boolean isUpdate() { return payload == null && success; }
}


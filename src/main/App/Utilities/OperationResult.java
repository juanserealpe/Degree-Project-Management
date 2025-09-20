package main.App.Utilities;
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
public class OperationResult {
    private final boolean success;
    private final String message;
    private final int errorCode;
    private final int rowsAffected;
    private final List<Map<String, Object>> payload;

    private OperationResult(boolean pSuccess, String pMessage, List<Map<String, Object>> pPayload, int pErrorCode, int pRowsAffected) {
        this.success = pSuccess;
        this.message = pMessage;
        this.errorCode = pErrorCode;
        this.payload = pPayload;
        this.rowsAffected = pRowsAffected;
    }
    //CREATORS//
    public static OperationResult CreateSuccessfulQueryResult(String pMessage, List<Map<String, Object>> pPayload){
        return new OperationResult(true, pMessage,pPayload,0,0);
    }
    public static OperationResult CreateErrorResult(String pMessage, int pErrorCode){
        return new OperationResult(false, pMessage,null,pErrorCode,0);
    }
    public static OperationResult CreateSuccessfulUpdateResult(String pMessage, int pRowsAffected){
        return new OperationResult(true, pMessage,null,0,pRowsAffected);
    }
    public static OperationResult CreateUnconfiguredResult(){
        return new OperationResult(false, "Unconfigured Script",null,0,0);
    }
    public int getRowsExtracted(){
        if(payload == null) return 0;
        else return payload.size();
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public int getErrorCode() { return errorCode; }
    public List<Map<String, Object>> getPayload() { return payload; }
    public int getRowsAffected() { return rowsAffected; }

    // IN PROCESS OF CREATION XD
    public boolean isQuery() { return errorCode == 0 && rowsAffected == 0 && success; }
    public boolean isUpdate() { return payload == null && errorCode == 0 && success; }
}


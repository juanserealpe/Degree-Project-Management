package Utilities;

import DataBase.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository {
    protected Connection connBd;
    protected DbOperationResult resultScript;

    public BaseRepository() {
        try{
            this.connBd = DbConnection.getConnection();
            loadConstraintForeingKey();
        }catch(Exception exc){
            System.out.println(exc.getMessage());
        }
        resultScript = DbOperationResult.CreateUnconfiguredResult();
    }
    private void loadConstraintForeingKey(){
        makeInsert("PRAGMA foreign_keys = OFF", null);
    }
    public DbOperationResult getOperationResult(){
        return resultScript;
    }
    private boolean HandleInvalidScript(){
        this.resultScript = DbOperationResult.CreateUnconfiguredResult();
        return false;
    }
    private boolean isValidScript(String pScript){
        return (!(pScript == null  || pScript.isEmpty()));
    }

    protected int makeInsertWithGeneratedKey(String pScript, Object[] pParamsToReplace) throws SQLException {
        if (!isValidScript(pScript)) {
            HandleInvalidScript();
            return -1;
        }
        try (PreparedStatement stmt = connBd.prepareStatement(pScript, Statement.RETURN_GENERATED_KEYS)) {
            if (pParamsToReplace != null) {
                for (int idx = 0; idx < pParamsToReplace.length; idx++) {
                    stmt.setObject(idx + 1, pParamsToReplace[idx]);
                }
            }
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                resultScript = DbOperationResult.createFailedInsertResult();
                return -1;
            }
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    resultScript = DbOperationResult.createSuccessfulInsertResult(rows);
                    return id;
                }
            }
            return -1;
        }
    }

    /*
     * Used from DML
     */
    protected boolean makeInsert(String pScript, Object[] pParamsToReplace){
        if(!(isValidScript(pScript))) return HandleInvalidScript();
        try{
            int vRowsAffected = executeUpdate(pScript, pParamsToReplace);
            if(vRowsAffected == 0)
                resultScript = DbOperationResult.createFailedInsertResult();
            else
                resultScript = DbOperationResult.createSuccessfulInsertResult(vRowsAffected);
        }catch(Exception exc){
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }
    protected boolean makeUpdate(String pScript, Object[] pParamsToReplace){
        if(!(isValidScript(pScript))) return HandleInvalidScript();
        try{
            int vRowsAffected = executeUpdate(pScript, pParamsToReplace);
            if(vRowsAffected == 0)
                resultScript = DbOperationResult.createFailedUpdateResult();
            else
                resultScript = DbOperationResult.createSuccessfulUpdateResult(vRowsAffected);
        }catch(Exception exc){
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }
    protected boolean makeRetrieve(String pScript, Object[] pParamsToReplace){
        if(!(isValidScript(pScript))) return HandleInvalidScript();
        try{
            List<Map<String, Object>> vData = executeQuery(pScript, pParamsToReplace);
            if(vData == null || vData.isEmpty())
                resultScript = DbOperationResult.createFailedRetrieveResult();
            else
                resultScript = DbOperationResult.createSuccessfulRetrieveResult(vData);
        }catch(Exception exc){
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }
    /*
     * Privates
     */
    private int executeUpdate(String pScript, Object[] pParams) throws Exception {
        try (PreparedStatement stmt = connBd.prepareStatement(pScript)) {
            if(pParams != null && pParams.length != 0){
                for (int idx = 0; idx < pParams.length; idx++)
                    stmt.setObject(idx + 1, pParams[idx]);
            }
            return stmt.executeUpdate();
        }
    }
    private List<Map<String, Object>> executeQuery(String pScript, Object[] pParams) throws Exception {
        List<Map<String, Object>> varResults = new ArrayList<>();
        try (PreparedStatement varStmt = connBd.prepareStatement(pScript)) {
            if (pParams != null && pParams.length != 0) {
                for (int idx = 0; idx < pParams.length; idx++)
                    varStmt.setObject(idx + 1, pParams[idx]);
            }
            try (ResultSet varResultSet = varStmt.executeQuery()) {
                ResultSetMetaData metaData = varResultSet.getMetaData();
                while (varResultSet.next()) {
                    Map<String, Object> currentRow = new HashMap<>();
                    for (int idx = 1; idx <= metaData.getColumnCount(); idx++)
                        currentRow.put(metaData.getColumnLabel(idx), varResultSet.getObject(idx));
                    varResults.add(currentRow);
                }
            }
        }
        return varResults;
    }
}


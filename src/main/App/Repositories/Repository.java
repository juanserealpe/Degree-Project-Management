package main.App.Repositories;

import main.App.DataBase.DbConnection;
import main.App.Utilities.OperationResult;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Repository{
    protected Connection connBd;
    protected OperationResult resultScript;

    public Repository() {
        try{
            this.connBd = DbConnection.getConnection();
        }catch(Exception exc){
            System.out.println(exc.getMessage());
        }
        resultScript = OperationResult.CreateUnconfiguredResult();
    }

    private boolean HandleInvalidScript(){
        this.resultScript = OperationResult.CreateUnconfiguredResult();
        return false;
    }
    private boolean isValidScript(String pScript){
        return (!(pScript == null  || pScript.isEmpty()));
    }
    /*
     * Used from DML and Query
     */
    protected boolean makeDml(String pScript, Object[] pParamsToReplace){
        if(!(isValidScript(pScript))) return HandleInvalidScript();
        try{
            resultScript = executeUpdate(pScript, pParamsToReplace);
        }catch(Exception exc){
            resultScript = this.typeOfError(exc);
        }
        return resultScript.isSuccess();
    }
    protected boolean makeQuery(String pScript, Object[] pParamsToReplace){
        if(pScript == null  || pScript.isEmpty()) return HandleInvalidScript();
        try{
            resultScript = executeQuery(pScript, pParamsToReplace);
        }catch(Exception exc){
            resultScript = this.typeOfError(exc);
        }
        return resultScript.isSuccess();
    }
    /*
     * Privates
     */
    private OperationResult executeUpdate(String pScript, Object[] pParams) throws Exception {
        try (PreparedStatement stmt = connBd.prepareStatement(pScript)) {
            if(pParams != null && pParams.length != 0){
                for (int idx = 0; idx < pParams.length; idx++)
                    stmt.setObject(idx + 1, pParams[idx]);
            }
            return OperationResult.CreateSuccessfulUpdateResult("Realized operation", stmt.executeUpdate());
        }
    }
    private OperationResult executeQuery(String pScript, Object[] pParams) throws Exception {
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
        return  OperationResult.CreateSuccessfulQueryResult("Correct", varResults);
    }
    private OperationResult typeOfError(Exception exc) {
        if (exc instanceof SQLiteException sqliteEx){
            return OperationResult.CreateErrorResult(sqliteEx.getMessage(), sqliteEx.getResultCode().code);
        }
        if(exc instanceof NullPointerException nullPointerExc)
            return OperationResult.CreateErrorResult(nullPointerExc.getMessage(), -1);
        return OperationResult.CreateErrorResult(exc.getMessage(), 99);
    }
}


package Utilities;

import java.sql.*;
import java.util.*;

public abstract class BaseRepository {
    protected final Connection connBd;   // conexión inyectada
    protected DbOperationResult resultScript;

    public BaseRepository(Connection connection) {
        this.connBd = connection;
        this.resultScript = DbOperationResult.CreateUnconfiguredResult();
    }

    public DbOperationResult getOperationResult() {
        return resultScript;
    }

    private boolean HandleInvalidScript() {
        this.resultScript = DbOperationResult.CreateUnconfiguredResult();
        return false;
    }

    private boolean isValidScript(String pScript) {
        return (!(pScript == null || pScript.isEmpty()));
    }

    // INSERT con generated keys
    protected int makeInsertWithGeneratedKey(String pScript, Object[] pParamsToReplace) throws SQLException {
        if (!isValidScript(pScript)) {
            HandleInvalidScript();
            return -1;
        }
        try (PreparedStatement stmt = connBd.prepareStatement(pScript, Statement.RETURN_GENERATED_KEYS)) {
            setParams(stmt, pParamsToReplace);
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

    // INSERT genérico
    protected boolean makeInsert(String pScript, Object[] pParamsToReplace) {
        if (!isValidScript(pScript)) return HandleInvalidScript();
        try {
            int vRowsAffected = executeUpdate(pScript, pParamsToReplace);
            if (vRowsAffected == 0)
                resultScript = DbOperationResult.createFailedInsertResult();
            else
                resultScript = DbOperationResult.createSuccessfulInsertResult(vRowsAffected);
        } catch (Exception exc) {
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }

    // UPDATE genérico
    protected boolean makeUpdate(String pScript, Object[] pParamsToReplace) {
        if (!isValidScript(pScript)) return HandleInvalidScript();
        try {
            int vRowsAffected = executeUpdate(pScript, pParamsToReplace);
            if (vRowsAffected == 0)
                resultScript = DbOperationResult.createFailedUpdateResult();
            else
                resultScript = DbOperationResult.createSuccessfulUpdateResult(vRowsAffected);
        } catch (Exception exc) {
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }

    // SELECT genérico
    protected boolean makeRetrieve(String pScript, Object[] pParamsToReplace) {
        if (!isValidScript(pScript)) return HandleInvalidScript();
        try {
            List<Map<String, Object>> vData = executeQuery(pScript, pParamsToReplace);
            if (vData == null || vData.isEmpty())
                resultScript = DbOperationResult.createFailedRetrieveResult();
            else
                resultScript = DbOperationResult.createSuccessfulRetrieveResult(vData);
        } catch (Exception exc) {
            resultScript = DbOperationResult.CreateErrorExceptionResult(exc.getMessage());
        }
        return resultScript.isSuccess();
    }

    // Helpers privados
    private int executeUpdate(String pScript, Object[] pParams) throws Exception {
        try (PreparedStatement stmt = connBd.prepareStatement(pScript)) {
            setParams(stmt, pParams);
            return stmt.executeUpdate();
        }
    }

    private List<Map<String, Object>> executeQuery(String pScript, Object[] pParams) throws Exception {
        List<Map<String, Object>> varResults = new ArrayList<>();
        try (PreparedStatement varStmt = connBd.prepareStatement(pScript)) {
            setParams(varStmt, pParams);
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

    private void setParams(PreparedStatement stmt, Object[] params) throws SQLException {
        if (params != null && params.length != 0) {
            for (int idx = 0; idx < params.length; idx++) {
                stmt.setObject(idx + 1, params[idx]);
            }
        }
    }
}

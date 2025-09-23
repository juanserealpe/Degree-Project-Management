package Utilities;

import java.sql.*;
import java.util.*;

/**
 * Repositorio base para operaciones de base de datos.
 *
 * Esta clase abstracta proporciona métodos genéricos para realizar operaciones CRUD
 * (INSERT, UPDATE, SELECT) sobre cualquier tabla de la base de datos.
 * Maneja resultados mediante {@link DbOperationResult} y permite la reutilización
 * de lógica común en todos los repositorios que hereden de esta clase.
 *
 * Los métodos implementan:
 * - Inserciones con o sin key generada.
 * - Actualizaciones genéricas.
 * - Consultas genéricas (SELECT).
 *
 * También incluye métodos auxiliares privados para ejecutar consultas y manejar parámetros.
 *
 * @author PonceAlex203
 */
public abstract class BaseRepository {

    protected final Connection connBd;   // Conexión a la base de datos inyectada
    protected DbOperationResult resultScript; // Resultado de la última operación SQL

    /**
     * Constructor que recibe la conexión a la base de datos.
     *
     * @param connection Conexión SQL activa.
     */
    public BaseRepository(Connection connection) {
        this.connBd = connection;
        this.resultScript = DbOperationResult.CreateUnconfiguredResult();
    }

    /**
     * Obtiene el resultado de la última operación SQL realizada.
     *
     * @return Resultado de la operación SQL.
     */
    public DbOperationResult getOperationResult() {
        return resultScript;
    }

    // Maneja scripts inválidos reiniciando el resultado
    private boolean HandleInvalidScript() {
        this.resultScript = DbOperationResult.CreateUnconfiguredResult();
        return false;
    }

    // Verifica si un script SQL es válido (no nulo ni vacío)
    private boolean isValidScript(String pScript) {
        return (!(pScript == null || pScript.isEmpty()));
    }

    /**
     * Inserta un registro en la base de datos y retorna la key generada.
     *
     * @param pScript SQL de inserción con placeholders.
     * @param pParamsToReplace Parámetros a reemplazar en el SQL.
     * @return ID generado por la base de datos; -1 si falla la inserción.
     * @throws SQLException Si ocurre un error de SQL.
     */
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

    /**
     * Inserción genérica sin retorno de key.
     *
     * @param pScript SQL de inserción.
     * @param pParamsToReplace Parámetros a reemplazar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Actualización genérica de registros.
     *
     * @param pScript SQL de actualización.
     * @param pParamsToReplace Parámetros a reemplazar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Consulta genérica (SELECT) de registros.
     *
     * @param pScript SQL de consulta.
     * @param pParamsToReplace Parámetros a reemplazar.
     * @return true si se obtuvieron resultados, false en caso contrario.
     */
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

    // Ejecuta un UPDATE o INSERT genérico
    private int executeUpdate(String pScript, Object[] pParams) throws Exception {
        try (PreparedStatement stmt = connBd.prepareStatement(pScript)) {
            setParams(stmt, pParams);
            return stmt.executeUpdate();
        }
    }

    // Ejecuta un SELECT genérico y retorna resultados
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

    // Asigna parámetros al PreparedStatement
    private void setParams(PreparedStatement stmt, Object[] params) throws SQLException {
        if (params != null && params.length != 0) {
            for (int idx = 0; idx < params.length; idx++) {
                stmt.setObject(idx + 1, params[idx]);
            }
        }
    }
}

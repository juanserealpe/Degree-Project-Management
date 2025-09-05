package App.Repositories;

import App.DataBase.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase abstracta para manejo de scripts en la bd(SQLITE).
 * @author Alexp
 */
public abstract class Repository{
    protected final DbConnection connBd; 
    protected String script;

    public Repository() {
        this.connBd = new DbConnection();
    }
    
    /**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE o DELETE) en la base de datos.
     *
     * @param pScript la sentencia SQL con parametros representados con '?'(opcional)
     * @param pParams los valores a sustituir en los parámetros de la sentencia, en ese mismo orden.
     * @return el número de filas afectadas por la operación, o -1 si ocurre un error.
     *
     * Ejemplo de uso:
     * <pre>
     * String sql = "UPDATE estudiantes SET nombre = ? WHERE id = ?";
     * Object[] parametros = {"Alex", 1};
     * int filas = ExecuteUpdate(sql, parametros);
     * </pre>
     */
    protected int ExecuteUpdate(String pScript, Object[] pParams) {
        try (java.sql.Connection conn = connBd.toConnect();
             PreparedStatement stmt = conn.prepareStatement(pScript)) {
            for (int idx = 0; idx < pParams.length; idx++) {
                stmt.setObject(idx + 1, pParams[idx]);
            }
            return stmt.executeUpdate();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return -1;
    }
    /**
    * Ejecuta una consulta SQL (SELECT) y retorna los resultados en una lista de maps.
    * Cada mapa representa una fila, donde la clave es el nombre de la columna
    * y el valor es el contenido de esa columna.
    *
    * @param pQuery la sentencia SQL con parámetros representados por '?'(opcional).
    * @param pParams los valores a sustituir en los parámetros de la sentencia, en el mismo orden (puede ser null).
    * @param columns el arreglo con los nombres de las columnas que quiere recuperarse de al consulta.
    * @return una lista de maps con los resultados de la consulta, o una lista vacía si ocurre un error.
    *
    * Ejemplo de uso:
    * <pre>
    * String sql = "SELECT id, nombre FROM estudiantes WHERE edad > ?";
    * Object[] parametros = {18};
    * String[] columnas = {"id", "nombre"};
    * List<Map<String, Object>> resultados = ExecuteQuery(sql, parametros, columnas);
    * </pre>
    */
    protected List<Map<String, Object>> ExecuteQuery(String pQuery, Object[] pParams, String[] columns) {
    List<Map<String, Object>> varResults = new ArrayList<>();   
    try (java.sql.Connection conn = connBd.toConnect();
         PreparedStatement varStmt = conn.prepareStatement(pQuery)) {
        if (pParams != null) {
            for (int idx = 0; idx < pParams.length; idx++)
                varStmt.setObject(idx + 1, pParams[idx]);
        } 
        try (ResultSet varResultSet = varStmt.executeQuery()) {
            while (varResultSet.next()) {
                Map<String, Object> currentRow = new HashMap<>();
                for (String varCurrentColumn : columns)
                    currentRow.put(varCurrentColumn, varResultSet.getObject(varCurrentColumn));
                varResults.add(currentRow);
            }
        }       
    } catch (Exception exc) {
        exc.printStackTrace();
    }
    return varResults;
}
}

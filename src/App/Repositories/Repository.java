package App.Repositories;

import App.DataBase.DbConnection;
import App.Utilities.Message;
import App.Utilities.MessageErrorRepository;
import App.Utilities.MessageQueryRepository;
import App.Utilities.MessageUpdateRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class from management of CRUDS at bd(SQLITE).
 *
 * @author Alexp
 *
 **/
public abstract class Repository{
    /**
    * Database connection handler
    **/
    protected final DbConnection connBd;
    /**
    * Contains the result of the last script
    **/
    protected Message resultScript;

    /**
     * Constructor initializes the database connection and sets
     * a default error message in case no script has been executed yet.
     */
    public Repository() {
        this.connBd = new DbConnection();
        resultScript = new MessageErrorRepository("Malconfigurate script", 100);
    }

    /**
     * Returns whether the last executed script was successful.
     *
     * @return true if the last script executed successfully, false otherwise.
     ***/
    public boolean getStateScript(){
        return resultScript.isExitoso();
    }

    /**
     * Returns the description or message of the last executed script.
     *
     * @return A string describing the result of the last script.
     */
    public String getDescriptionScript(){
        return resultScript.getDescription();
    }

    /**
     * Check whether an array is null or empty.
     *
     * @param pArray The array to check.
     * @return true if the array is null or has zero length, false otherwise.
     */
    private boolean isEmpty(Object[] pArray){
        return(pArray == null || pArray.length == 0);
    }

    /**
     * Retrieves the query result data for the specified columns.
     *
     * @param pColumns The columns to extract from the last query result.
     * @return An array of Maps representing the rows of the query result.
     *         Returns an empty array if the last result is not a query
     *         or if the column array is null or empty.
     */
    protected Object[] getDataFromColumns(Object[] pColumns){
        if(!(resultScript instanceof MessageQueryRepository)) return new Object[0];
        if(isEmpty(pColumns)) return new Object[0];
        List<Map<String, Object>> varData = ((MessageQueryRepository)(resultScript)).getData();
        return varData.toArray();
    }

    /**
     * Executes a non-query SQL script (INSERT, UPDATE, DELETE) with optional parameters.
     *
     * @param pScript The SQL script to execute.
     * @param pParamsToReplace Array of objects to replace parameters in the script.
     * @return true if the operation was successful, false if an error occurred.
     *
     * If an exception occurs, it is caught and converted into a MessageErrorRepository,
     * allowing the caller to inspect the error without handling exceptions directly.
     */
    protected boolean executeScript(String pScript, Object[] pParamsToReplace){
        if(pScript.isEmpty()) return resultScript.isExitoso();
        try{
            resultScript = executeUpdate(pScript, pParamsToReplace);
        }catch(Exception exc){
            resultScript = this.typeOfError(exc);
        }
        return resultScript.isExitoso();
    }

    /**
     * Executes a SELECT SQL script and retrieves specified columns.
     *
     * @param pScript The SQL SELECT script to execute.
     * @param pParamsToReplace Array of objects to replace parameters in the script.
     * @param columnsToRetrieve Array of column names to extract from the query result.
     * @return true if the query was successful, false if an error occurred.
     *
     * The query result is stored in resultScript as a MessageQueryRepository,
     * which can later be accessed using GetDataFromColumns().
     */
    protected boolean executeScript(String pScript, Object[] pParamsToReplace, String[] columnsToRetrieve){
        if(pScript.isEmpty())  return resultScript.isExitoso();
        if(isEmpty(columnsToRetrieve))  return resultScript.isExitoso();
        try{
            resultScript = executeQuery(pScript, pParamsToReplace, columnsToRetrieve);
        }catch(Exception exc){
            resultScript = this.typeOfError(exc);
        }
        return resultScript.isExitoso();
    }

    /**
     * Executes a non-query SQL script (INSERT, UPDATE, DELETE) using a prepared statement.
     *
     * @param pScript The SQL script to execute.
     * @param pParams Array of objects to replace the script parameters (can be null or empty).
     * @return A MessageUpdateRepository containing the number of affected rows and a success message.
     * @throws SQLException If there is a database access error or the SQL script is invalid.
     *
     * This method automatically sets the parameters in the prepared statement if provided.
     * It uses try-with-resources to ensure the Connection and PreparedStatement are closed properly.
     */
    private MessageUpdateRepository executeUpdate(String pScript, Object[] pParams) throws SQLException {
        try (java.sql.Connection conn = connBd.toConnect();
             PreparedStatement stmt = conn.prepareStatement(pScript)) {
            if(!(isEmpty(pParams))){
                for (int idx = 0; idx < pParams.length; idx++) {
                    stmt.setObject(idx + 1, pParams[idx]);
                }
            }
            return new MessageUpdateRepository("Successful operation", stmt.executeUpdate());
        }
    }

    /**
     * Executes a SELECT SQL script using a prepared statement and retrieves specified columns.
     *
     * @param pScript The Sql SELECT script to execute.
     * @param pParams Array of objects to replace the SQL script parameters (can be null or empty).
     * @param columns Array of column names to extract from the query result.
     * @return A MessageQueryRepository containing the query result as a list of maps
     *         where each map represents a row with column-value pairs, and a success message.
     * @throws SQLException If there is a database access error or the SQL script is invalid.
     *
     * This method automatically sets the parameters in the prepared statement if provided.
     * Each row from the ResultSet is converted into a Map<String, Object> using the specified columns.
     * It uses try-with-resources to ensure Connection, PreparedStatement, and ResultSet are closed properly.
     */
    private MessageQueryRepository executeQuery(String pScript, Object[] pParams, String[] columns) throws SQLException {
        List<Map<String, Object>> varResults = new ArrayList<>();
        try (java.sql.Connection conn = connBd.toConnect(); PreparedStatement varStmt = conn.prepareStatement(pScript)) {
            if (!(isEmpty(pParams))) {
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
        }
        return new MessageQueryRepository("Correct", varResults);
    }

    /**
     * Converts exceptions into a standardized MessageErrorRepository.
     *
     * @param exc The exception thrown during operation database.
     * @return A MessageErrorRepository containing the error message and code.
     *
     * Current supports SQLiteException and NullPointerException explicitly.
     * Other exceptions are returned with a default error code of 0.
     */
    private MessageErrorRepository typeOfError(Exception exc) {
        if (exc instanceof org.sqlite.SQLiteException sqliteEx){
            return new MessageErrorRepository(sqliteEx.getMessage(), sqliteEx.getResultCode().code);
        }
        if(exc instanceof NullPointerException nullPointerExc)
            return new MessageErrorRepository(nullPointerExc.getMessage(), nullPointerExc.hashCode());
        return new MessageErrorRepository(exc.getMessage(), 0);
    }
}

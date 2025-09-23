package Utilities;

import java.util.List;
import java.util.Map;

/**
 * Clase que representa el resultado de una operación en la base de datos.
 *
 * Esta clase almacena información sobre si la operación fue exitosa,
 * un mensaje descriptivo, el número de filas afectadas y, en caso de
 * consultas, el payload con los datos recuperados.
 * <p>
 * Permite crear resultados específicos para inserciones, actualizaciones
 * y consultas, así como manejar errores y resultados no configurados.
 * </p>
 *
 * @author PonceAlex203
 */
public class DbOperationResult {

    private final boolean success;                       // Indica si la operación fue exitosa
    private final String message;                        // Mensaje descriptivo del resultado
    private final int rowsAffected;                      // Número de filas afectadas por INSERT/UPDATE
    private final List<Map<String, Object>> payload;     // Datos recuperados por un SELECT

    /**
     * Constructor privado que inicializa todas las propiedades.
     *
     * @param pSuccess Estado de éxito de la operación.
     * @param pMessage Mensaje descriptivo del resultado.
     * @param pPayload Datos recuperados (para SELECT).
     * @param pRowsAffected Número de filas afectadas (para INSERT/UPDATE).
     */
    private DbOperationResult(boolean pSuccess, String pMessage, List<Map<String, Object>> pPayload, int pRowsAffected) {
        this.success = pSuccess;
        this.message = pMessage;
        this.payload = pPayload;
        this.rowsAffected = pRowsAffected;
    }

    // ==================== CREATORS ====================

    public static DbOperationResult createSuccessfulInsertResult(int pRowsAffected){
        return new DbOperationResult(true, Messages.getMessageSuccessfulInsertWithRowsAffected(pRowsAffected), null, pRowsAffected);
    }

    public static DbOperationResult createSuccessfulUpdateResult(int pRowsAffected){
        return new DbOperationResult(true, Messages.getMessageSuccessfulUpdateWithRowsAffected(pRowsAffected), null, pRowsAffected);
    }

    public static DbOperationResult createSuccessfulRetrieveResult(List<Map<String, Object>> pPayload){
        return new DbOperationResult(true, Messages.getMessageSuccessfulRetrieveWithCountRows(pPayload.size()), pPayload, 0);
    }

    public static DbOperationResult createFailedInsertResult(){
        return new DbOperationResult(false, Messages.getMessageFailedInsert(), null, 0);
    }

    public static DbOperationResult createFailedUpdateResult(){
        return new DbOperationResult(false, Messages.getMessageFailedUpdate(), null, 0);
    }

    public static DbOperationResult createFailedRetrieveResult(){
        return new DbOperationResult(false, Messages.getMessageFailedRetrieve(), null, 0);
    }

    public static DbOperationResult CreateErrorExceptionResult(String pMessage){
        return new DbOperationResult(false, pMessage, null, 0);
    }

    public static DbOperationResult CreateUnconfiguredResult(){
        return new DbOperationResult(false, "Unconfigured", null, 0);
    }

    // ==================== GETTERS ====================

    /**
     * Retorna la cantidad de filas extraídas de un SELECT.
     *
     * @return Número de filas en el payload.
     */
    public int getRowsExtracted(){
        if(payload == null) return 0;
        return payload.size();
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Map<String, Object>> getPayload() { return payload; }
    public int getRowsAffected() { return rowsAffected; }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Indica si el resultado corresponde a una consulta (SELECT).
     *
     * @return true si es SELECT exitoso, false en otro caso.
     */
    public boolean isQuery() { return rowsAffected == 0 && success; }

    /**
     * Indica si el resultado corresponde a una actualización (INSERT/UPDATE).
     *
     * @return true si es INSERT/UPDATE exitoso, false en otro caso.
     */
    public boolean isUpdate() { return payload == null && success; }
}

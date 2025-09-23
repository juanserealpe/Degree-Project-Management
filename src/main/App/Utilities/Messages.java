package Utilities;

public class Messages {
    private Messages(){
    }
    public static String getMessageNotFound(){
        return getMessageOperationError() + "Data not found ";
    }
    public static String getMessageOperationRealized(){
        return "Operation realized ";
    }
    public static String getMessageOperationFailed(){
        return "Operation Failed ";
    }
    public static String getSuccessfulMessage(){
        return "Successful ";
    }
    public static String getMessageOperationError(){
        return "Error ";
    }
    public static String getMessageFailed(){
        return "Failed ";
    }
    public static String getMessageRowsAffected(int pRowAffected){
        return pRowAffected + " rows affected. ";
    }
    public static String getMessageSuccessfulUpdate(){
        return getSuccessfulMessage() + "update!. ";
    }
    public static String getMessageSuccessfulInsert(){
        return getSuccessfulMessage() + "registration!. ";
    }
    public static String getMessageSuccessfulRetrieve(){
        return getSuccessfulMessage() + "retrieve!. ";
    }
    public static String getMessageFailedUpdate(){
        return getMessageFailed() + "update!. 0 rows affected";
    }
    public static String getMessageFailedInsert(){
        return getMessageFailed() + "insertion!. 0 rows inserted";
    }
    public static String getMessageFailedRetrieve(){
        return getMessageFailed() + "recovery!. 0 rows found";
    }
    public static String getMessageSuccessfulUpdateWithRowsAffected(int pRowsAffected){
        return getMessageSuccessfulUpdate() + getMessageRowsAffected(pRowsAffected);
    }
    public static String getMessageSuccessfulInsertWithRowsAffected(int pRowsAffected){
        return getMessageSuccessfulInsert() + getMessageRowsAffected(pRowsAffected);
    }
    public static String getMessageSuccessfulRetrieveWithCountRows(int pCountRows){
        return getMessageSuccessfulRetrieve() + pCountRows + " rows found.";
    }
    public static String getMessageFailedNotFound(){
        return getMessageFailed() + getMessageNotFound();
    }
}

package App.Utilities;

/**
 * Represents the result of an UPDATE/INSERT/DELETE operation in a repository.
 *
 * Stores the number of rows affected by the operation and a success message.
 **/
public class MessageUpdateRepository extends Message{
    /** Number of rows affected by the operation. **/
    protected int numberRowsAffected;

    /**
     * Constructs a MessageUpdateRepository with a description and affected row count.
     *
     * @param pMessage Description of the operation result
     * @param pNumberRowsAffected Number of rows affected by the operation
     **/
    public MessageUpdateRepository(String pMessage, int pNumberRowsAffected) {
        super(true, pMessage);
        numberRowsAffected = pNumberRowsAffected;
    }

    /** Returns the number of rows affected by the operation. **/
    public int getNumberRowsAffected() {
        return numberRowsAffected;
    }
}

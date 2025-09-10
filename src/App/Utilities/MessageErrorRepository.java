package App.Utilities;


/**
 * Represents an error result from a repository operation.
 *
 * Extends Message and adds an error code for more specific error handling.
 */
public class MessageErrorRepository extends Message{
    /** Error code representing the type of repository error. **/
    protected int code;

    /**
     * Constructs a MessageErrorRepository with a description and code.
     *
     * @param pDescription Error massage description
     * @param pCode Error code representing the type of error
     **/
    public MessageErrorRepository(String pDescription, int pCode) {
        super(false, pDescription);
        this.code = pCode;
    }

    /** Returns the error code associated with this message*. */
    public int getCode() {
        return code;
    }
}

package App.Utilities;

/**
 * Base class representing the result of an operation.
 *
 * Stores whether the operation was successful and a descriptive message.
 * Can be extended to include more detailed information for specific cases.
 */
public class Message {
    /** result from an operation    **/
    protected final boolean exitoso;
    /** Description of the result or error message. */
    protected final String description;

    /**
     * Constructs a Message object with the given status and description.
     *
     * @param pExitoso true if the operation succeeded, false otherwise
     * @param pDescription Description of the result or error
     */
    public Message(boolean pExitoso, String pDescription) {
        this.exitoso = pExitoso;
        this.description = pDescription;
    }

    /** Returns true if the operation was successful. */
    public boolean isExitoso() {
        return exitoso;
    }

    /** Returns the description of the result or error. */
    public String getDescription(){
        return description;
    }
}

import java.lang.RuntimeException;

/* {@code InputException} class handles the errors that
 * might occur during the parsing of input into the InputParser object.
 * <p>
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */

public class InputException extends RuntimeException {

    /* Serialization Identifier */

    private static final long serialVersionUID = 1L;

    /**
     * constructor instantiates an InputException instance
     *
     * @param errorMessage An error message to shown to the user.
     */

    public InputException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * A constructor with arguments, an instantiates a
     * InputException instance with a default error
     * message
     */

    public InputException() {
        super("An unknown error occured.");
    }
}

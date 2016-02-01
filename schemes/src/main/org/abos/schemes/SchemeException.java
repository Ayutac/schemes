// LICENSE
package org.abos.schemes;

/**
 * The base exception for scheme exceptions.
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class SchemeException extends RuntimeException {

    // Javadoc exception docs

    /**
     * 
     */
    public SchemeException() {
        super();
    }

    /**
     * @param message
     */
    public SchemeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SchemeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SchemeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SchemeException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

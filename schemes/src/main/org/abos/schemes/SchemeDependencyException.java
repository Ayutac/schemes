package org.abos.schemes;

/**
 * Javadoc document this
 * 
 * @author Sebastian Koch
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class SchemeDependencyException extends SchemeException {
    
    // Javadoc exception documentation

    /**
     * 
     */
    public SchemeDependencyException() {}

    /**
     * @param message
     */
    public SchemeDependencyException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SchemeDependencyException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SchemeDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SchemeDependencyException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

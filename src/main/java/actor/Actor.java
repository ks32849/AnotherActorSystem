package actor;

/**
 * An Actor is a self-contained unit with its own state running in its own thread
 * communicating to other actors via messages.
 * Thus it is used to obviate lock-based synchronization in classic concurrency models.
 * 
 */
public interface Actor {
    
    /**
     * An Actor needs to provide routes that map Commands to their CommandHandlers.
     * Route provides a RouteBuilder for this purpose.
     * 
     * @return Route 
     */
    Route routes();
}
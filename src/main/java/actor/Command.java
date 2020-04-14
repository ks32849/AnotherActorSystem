package actor;

/**
 * Actors communicate by exchanging Commands. 
 * 
 * Actors should provide implementations of this interface for that purpose.
 * It is good practise to define all of an Actors commands as public static inner
 * classes making it easy for the sender to see which commands are supported by
 * an actor.
 */
public interface Command {
}
